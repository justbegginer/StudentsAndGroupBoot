package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public List<Student> getAllStudents(Model model) {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") int id) {
        // TODO make redirect to error 404
        return studentService.findById(id).get();
    }

    @PostMapping()
    @Transactional()
    public Status addNewStudentToDB(@RequestBody Map<String, Map<String, String>> request) {
        Student student = new Student();
        student.setName(request.get("student").get("name"));
        student.setSurname(request.get("student").get("surname"));
        int groupId = 0;
        try{
            groupId = Integer.parseInt(request.get("student").get("groupNumber"));
        }
        catch (NumberFormatException e){
            return new Status(false, StatusPattern.INVALID,
                    "Group id = '"+request.get("student").get("groupNumber")+"' not valid");
        }
        student.setGroupNumber(groupId);
        studentService.save(student);
        User user = new User();
        user.setEmail(request.get("user").get("email"));
        user.setPassword(request.get("user").get("password"));
        user.setRole("student");
        user.setUserId(user.getId());
        user.setLoginBasedOnEmail();
        userService.save(user);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @DeleteMapping("{id}")
    @Transactional
    public Status deleteStudentFromDB(@PathVariable("id") int id) {
        if (studentService.findById(id).isEmpty()){
            return new Status(false, StatusPattern.NOT_FOUND, null);
        }
        studentService.delete(studentService.findById(id).get());
        userService.delete(userService.findTopByRoleAndUserId("student", id));
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @PatchMapping("{id}")
    public Status updateGroup(@RequestBody @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error in fields ");
            for (String suppressedField : bindingResult.getSuppressedFields()) {
                errorMessage.append(suppressedField);
            }
            return new Status(false, StatusPattern.INVALID,errorMessage.toString());
        }
        if (studentService.findById(student.getId()).isEmpty()){
            return new Status(false, StatusPattern.NOT_FOUND, null);
        }
        studentService.save(student);
        return new Status(true, StatusPattern.SUCCESS, null);
    }
}