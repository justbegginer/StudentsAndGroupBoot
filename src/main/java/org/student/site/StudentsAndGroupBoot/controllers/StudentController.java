package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.services.impl.StudentServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;


@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentServiceImpl studentService;

    private final UserServiceImpl userService;

    public StudentController(@Autowired StudentServiceImpl studentService,
                             @Autowired UserServiceImpl userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student/all";
    }

    @GetMapping("/{id}")
    public String getStudentById(@PathVariable("id") int id, Model model) {
        if (studentService.findById(id).isEmpty()) {
            model.addAttribute("message", "Student with id = " + id + " not found");
            return "errors/error404";
        }
        model.addAttribute("student", studentService.findById(id).get());
        return "student/getStudent";
    }

    @GetMapping("/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("user", new User());
        return "student/add";
    }

    @PostMapping()
    @Transactional()
    public String addNewStudentToDB(@ModelAttribute("student") @Valid Student student,
                                    @ModelAttribute("user") @Valid User user,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "student/add";
        }
        studentService.save(student);
        user.setRole("student");
        user.setUserId(studentService.findTopByOrderByIdDesc().getId());
        user.setLoginBasedOnEmail();
        userService.save(user);
        return "redirect:/students";
    }

    @GetMapping("{id}/delete")
    public String pageToDelete(@PathVariable("id") int id, Model model) {
        if (studentService.findById(id).isEmpty()) {
            model.addAttribute("message", "Student with id = " + id + " not found");
            return "error404";
        }
        model.addAttribute("student", studentService.findById(id).get());
        return "student/delete";
    }

    @DeleteMapping("{id}")
    @Transactional
    public String deleteStudentFromDB(@PathVariable("id") int id) {
        studentService.delete(studentService.findById(id).get());
        userService.delete(userService.findTopByRoleAndUserId("student", id));
        return "redirect:/students";
    }

    @GetMapping("{id}/update")
    public String pageToUpdate(@PathVariable("id") int id, Model model) {
        if (studentService.findById(id).isEmpty()) {
            model.addAttribute("message", "Student with id = " + id + " not found");
            return "error404";
        }
        model.addAttribute("student", studentService.findById(id).get());
        return "student/update";
    }

    @PatchMapping("{id}")
    public String updateGroup(@ModelAttribute("student") @Valid Student student,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "student/update";
        }
        studentService.save(student);
        model.addAttribute("students", studentService.findAll());
        return "student/all";
    }
}
