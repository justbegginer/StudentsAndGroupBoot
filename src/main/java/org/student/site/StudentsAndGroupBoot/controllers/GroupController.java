package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.models.CompleteGroup;
import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.repo.GroupRepo;
import org.student.site.StudentsAndGroupBoot.repo.StudentRepo;
import org.student.site.StudentsAndGroupBoot.repo.TutorRepo;

import javax.validation.Valid;

@Controller
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TutorRepo tutorRepo;

    @Autowired
    private GroupRepo groupRepo;

    @GetMapping
    public String getAllGroups(Model model) {
        model.addAttribute("groups", groupRepo.findAll());
        return "group/all";
    }

    @GetMapping("/{id}")
    public String getGroupById(@PathVariable("id") int id, Model model,
                               @RequestParam(value = "fullinfo", required = false) boolean fullInfo) {
        if (groupRepo.findById(id).isEmpty()) {
            model.addAttribute("message", "Group with id = " + id + " not found");
            return "error404";
        }
        if (!fullInfo) {
            model.addAttribute("group", groupRepo.findById(id).get());
            return "group/getGroup";
        } else {
            CompleteGroup completeGroup = new CompleteGroup(groupRepo.findById(id).get(),
                    tutorRepo.findById(groupRepo.findById(id).get().getTutorId()).get(),
                    studentRepo.findStudentByGroupNumber(id));
            model.addAttribute("completeGroup", completeGroup);
            //TODO rebuild from old repositories
            return "group/getGroupWithAllInfo";
        }
    }

    @GetMapping("/new")
    public String newGroup(Model model) {
        model.addAttribute("group", new Group());
        return "group/add";
    }

    @PostMapping()
    public String addNewGroupToDB(@ModelAttribute("group") @Valid Group group,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "group/add";
        }
        groupRepo.save(group);
        //groupRepo.save(group);
        return "redirect:/groups";
    }

    @GetMapping("{id}/delete")
    public String pageToDelete(@PathVariable("id") int id, Model model) {
        if (groupRepo.findById(id).isEmpty()) {
            model.addAttribute("message", "Group with id = " + id + " not found");
            return "error404";
        }
        model.addAttribute("group", groupRepo.findById(id).get());
        return "group/delete";
    }

    @DeleteMapping("{id}")
    public String deleteGroupFromDB(@PathVariable("id") int id) {
        groupRepo.delete(groupRepo.findById(id).get());
        return "redirect:/groups";
    }

    @GetMapping("{id}/update")
    public String pageToUpdate(@PathVariable("id") int id, Model model) {
        if (groupRepo.findById(id).isEmpty()) {
            model.addAttribute("message", "Group with id = " + id + " not found");
            return "error404";
        }
        model.addAttribute("group", groupRepo.findById(id).get());
        return "group/update";
    }

    @PatchMapping("{id}")
    public String updateGroup(@ModelAttribute("group") @Valid Group group,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "group/update";
        }
        groupRepo.delete(groupRepo.findById(group.getId()).get());
        groupRepo.save(group);
        model.addAttribute("groups", groupRepo.findAll());
        return "group/all";
    }
}
