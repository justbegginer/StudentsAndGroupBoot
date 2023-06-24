package org.student.site.StudentsAndGroupBoot.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.dto.StudentUserDto;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.Status;
import org.student.site.StudentsAndGroupBoot.models.StatusPattern;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.services.impl.StudentServiceImpl;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/rest/students")
public class StudentRestController {
    private final StudentServiceImpl studentService;


    public StudentRestController(@Autowired StudentServiceImpl studentService) {
        this.studentService = studentService;
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
    public Status addNewStudentToDB(@RequestBody StudentUserDto studentUserDto) {
        studentService.save(studentUserDto.getStudent(), studentUserDto.getUser());
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @DeleteMapping("{id}")
    public Status deleteStudentFromDB(@PathVariable("id") int id) {
        studentService.delete(id);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @PatchMapping
    public Status updateGroup(@RequestBody Student student) {
        studentService.update(student);
        return new Status(true, StatusPattern.SUCCESS, null);
    }
}