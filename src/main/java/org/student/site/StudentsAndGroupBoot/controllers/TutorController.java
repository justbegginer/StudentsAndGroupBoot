package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.Tutor;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.services.impl.GroupServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.StudentServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.TutorServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tutors")
public class TutorController {
    @Autowired
    private TutorServiceImpl tutorService;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private GroupServiceImpl groupService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public String getAllTutors(Model model) {
        model.addAttribute("tutors", tutorService.findAll());
        return "/tutor/all";
    }

    @GetMapping("/{id}")
    public String getTutorById(@PathVariable("id") int id, Model model,
                               @RequestParam(value = "withAllStudents", required = false) boolean fullInfo) {
        if (tutorService.findById(id).isEmpty()) {
            model.addAttribute("message", "Tutor with id = " + id + " not found");
            return "errors/error404";
        }
        model.addAttribute("tutor", tutorService.findById(id).get());
        if (fullInfo) {
            List<List<Student>> list = new ArrayList<>();
            int size = groupService.findGroupByTutorId(id).size();
            for (int i = 0; i < size; i++) {
                list.add(studentService.findStudentByGroupNumber(
                        groupService.findGroupByTutorId(id).get(i).getId()));
            }
            model.addAttribute("students", list);
            return "/tutor/getTutorWithAllInfo";
        } else {
            return "tutor/getTutor";
        }
    }

    @GetMapping("/new")
    public String newTutor(Model model) {
        model.addAttribute("tutor", new Tutor());
        model.addAttribute("user", new User());
        return "tutor/add";
    }

    @PostMapping()
    public String addNewTutorToDB(@ModelAttribute("tutor") @Valid Tutor tutor,
                                  BindingResult bindingResult,
                                  @ModelAttribute("user") User user) {
        if (bindingResult.hasErrors()) {
            return "tutor/add";
        }
        tutorService.save(tutor);
        user.setRole("tutor");
        user.setUserId(tutorService.findTopByOrderByIdDesc().getId());
        user.setLoginBasedOnEmail();
        userService.save(user);
        return "redirect:/tutors";
    }

    @GetMapping("{id}/delete")
    public String pageToDelete(@PathVariable("id") int id, Model model) {
        if (tutorService.findById(id).isEmpty()) {
            model.addAttribute("message", "Tutor with id = " + id + " not found");
            return "error404";
        }
        model.addAttribute("tutor", tutorService.findById(id).get());
        return "tutor/delete";
    }

    @DeleteMapping("{id}")
    public String deleteTutorFromDB(@PathVariable("id") int id) {
        tutorService.delete(tutorService.findById(id).get());
        userService.delete(userService.findTopByRoleAndUserId("tutor", id));
        return "redirect:/tutors";
    }

    @GetMapping("{id}/update")
    public String pageToUpdate(@PathVariable("id") int id, Model model) {
        if (tutorService.findById(id).isEmpty()) {
            model.addAttribute("message", "Tutor with id = " + id + " not found");
            return "error404";
        }
        model.addAttribute("tutor", tutorService.findById(id).get());
        return "tutor/update";
    }

    @PatchMapping("{id}")
    public String updateGroup(@ModelAttribute("tutor") @Valid Tutor tutor,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "tutor/update";
        }
        tutorService.save(tutor);
        model.addAttribute("tutors", tutorService.findAll());
        return "tutor/all";
    }
}
