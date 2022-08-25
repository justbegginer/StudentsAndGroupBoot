package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.repo.StudentRepo;

import javax.validation.Valid;


@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentRepo studentRepo;

    @GetMapping
    public String getAllStudents(Model model) {
        model.addAttribute("students", studentRepo.findAll());
        return "student/all";
    }

    @GetMapping("/{id}")
    public String getStudentById(@PathVariable("id") int id, Model model) {
        if (studentRepo.findById(id).isEmpty()) {
            model.addAttribute("message", "Student with id = " + id + " not found");
            return "error404";
        }
        model.addAttribute("student", studentRepo.findById(id).get());
        return "student/getStudent";
    }

    @GetMapping("/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        return "student/add";
    }

    @PostMapping()
    public String addNewStudentToDB(@ModelAttribute("student") @Valid Student student,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "student/add";
        }
        studentRepo.save(student);
        return "redirect:/students";
    }

    @GetMapping("{id}/delete")
    public String pageToDelete(@PathVariable("id") int id, Model model) {
        if (studentRepo.findById(id).isEmpty()) {
            model.addAttribute("message", "Student with id = " + id + " not found");
            return "error404";
        }
        model.addAttribute("student", studentRepo.findById(id).get());
        return "student/delete";
    }

    @DeleteMapping("{id}")
    public String deleteStudentFromDB(@PathVariable("id") int id) {
        studentRepo.delete(studentRepo.findById(id).get());
        return "redirect:/students";
    }

    @GetMapping("{id}/update")
    public String pageToUpdate(@PathVariable("id") int id, Model model) {
        if (studentRepo.findById(id).isEmpty()) {
            model.addAttribute("message", "Student with id = " + id + " not found");
            return "error404";
        }
        model.addAttribute("student", studentRepo.findById(id).get());
        return "student/update";
    }

    @PatchMapping("{id}")
    public String updateGroup(@ModelAttribute("student") @Valid Student student,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "student/update";
        }
        studentRepo.delete(studentRepo.findById(student.getId()).get());
        studentRepo.save(student);
        model.addAttribute("students", studentRepo.findAll());
        return "student/all";
    }
}
