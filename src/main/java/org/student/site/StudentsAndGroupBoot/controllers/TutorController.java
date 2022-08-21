package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.models.Tutor;
import org.student.site.StudentsAndGroupBoot.repo.TutorRepo;

import javax.validation.Valid;

@Controller
@RequestMapping("/tutors")
public class TutorController {
    @Autowired
    private TutorRepo tutorRepo;

    @GetMapping
    public String getAllTutors(Model model) {
        model.addAttribute("tutors", tutorRepo.findAll());
        return "/tutor/all";
    }

    @GetMapping("/{id}")
    public String getTutorById(@PathVariable("id") int id, Model model) {
        model.addAttribute("tutor", tutorRepo.findById(id).get());
        return "tutor/getTutor";
    }

    @GetMapping("/new")
    public String newTutor(Model model) {
        model.addAttribute("tutor", new Tutor());
        return "tutor/add";
    }

    @PostMapping()
    public String addNewTutorToDB(@ModelAttribute("tutor") @Valid Tutor tutor,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "tutor/add";
        }
        tutorRepo.save(tutor);
        return "redirect:/tutors";
    }

    @GetMapping("{id}/delete")
    public String pageToDelete(@PathVariable("id") int id, Model model) {
        model.addAttribute("tutor", tutorRepo.findById(id).get());
        return "tutor/delete";
    }

    @DeleteMapping("{id}")
    public String deleteTutorFromDB(@PathVariable("id") int id) {
        tutorRepo.delete(tutorRepo.findById(id).get());
        return "redirect:/tutors";
    }

    @GetMapping("{id}/update")
    public String pageToUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("tutor", tutorRepo.findById(id).get());
        return "tutor/update";
    }

    @PatchMapping("{id}")
    public String updateGroup(@ModelAttribute("tutor") @Valid Tutor tutor,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "tutor/update";
        }
        tutorRepo.delete(tutorRepo.findById(tutor.getId()).get());
        tutorRepo.save(tutor);
        model.addAttribute("tutors", tutorRepo.findAll());
        return "tutor/all";
    }
}
