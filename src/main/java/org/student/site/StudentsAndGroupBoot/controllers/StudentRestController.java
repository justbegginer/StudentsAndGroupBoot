package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.Utils;
import org.student.site.StudentsAndGroupBoot.dto.StudentUserDto;
import org.student.site.StudentsAndGroupBoot.exceptions.IncorrectDataException;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.Status;
import org.student.site.StudentsAndGroupBoot.models.StatusPattern;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.services.impl.StudentServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/students")
public class StudentRestController {
    private final StudentServiceImpl studentService;

    private final UserServiceImpl userService;

    public StudentRestController(@Autowired StudentServiceImpl studentService,
                                 @Autowired UserServiceImpl userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        if (studentService.findAll().isEmpty()) {
            throw new NotFoundException("There is no one student");
        }
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") int id) {
        if (studentService.findById(id).isEmpty()) {
            throw new NotFoundException("There is no student with id = " + id);
        }
        return studentService.findById(id).get();
    }

    @PostMapping()
    @Transactional()
    public Status addNewStudentToDB(@RequestBody StudentUserDto studentUserDto) {
        studentService.save(studentUserDto.getStudent());
        User user = studentUserDto.getUser();
        user.setRole("student");
        user.setUserId(studentService.findTopByOrderByIdDesc().getId());
        user.setLoginBasedOnEmail();
        userService.save(user);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @DeleteMapping("{id}")
    @Transactional
    public Status deleteStudentFromDB(@PathVariable("id") int id) {
        if (studentService.findById(id).isEmpty()) {
            throw new NotFoundException("Student with id = " + id + " doesn't exist");
        }
        studentService.delete(studentService.findById(id).get());
        userService.delete(userService.findTopByRoleAndUserId("student", id).get());
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @PatchMapping("{id}")
    public Status updateGroup(@RequestBody @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectDataException(Utils.getErrorStatusFromBindingResult(bindingResult));
        }
        if (studentService.findById(student.getId()).isEmpty()) {
            throw new NotFoundException("Student with id = " + student.getId() + " doesn't exist");
        }
        studentService.save(student);
        return new Status(true, StatusPattern.SUCCESS, null);
    }
}