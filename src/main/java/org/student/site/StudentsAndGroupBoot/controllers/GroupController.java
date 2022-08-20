package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.repo.GroupRepo;

import javax.validation.Valid;

@Controller
@RequestMapping("/groups")
public class GroupController {
    //@Autowired
    //private GroupDao groupDao;

    @Autowired
    private GroupRepo groupRepo;

    @GetMapping
    public String getAllGroups(Model model) {
        System.out.println(groupRepo.findAll().isEmpty());
        model.addAttribute("groups", groupRepo.findAll());
        return "group/all";
    }

    @GetMapping("/{id}")
    public String getGroupById(@PathVariable("id") int id, Model model,
                               @RequestParam(value = "fullinfo", required = false) boolean fullInfo) {
        if (!fullInfo) {
            model.addAttribute("group", groupRepo.findById(id).get());
            return "group/getGroup";
        }
        else{
            //model.addAttribute("completeGroup", groupDao.getCompleteGroupById(id));
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
        if (bindingResult.hasErrors()){
            return "group/add";
        }
        groupRepo.save(group);
        //groupRepo.save(group);
        return "redirect:/groups";
    }

    @GetMapping("{id}/delete")
    public String pageToDelete(@PathVariable("id") int id, Model model) {
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
        model.addAttribute("group", groupRepo.findById(id).get());
        return "group/update";
    }

    @PatchMapping("{id}")
    public String updateGroup(@ModelAttribute("group") @Valid Group group,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "group/update";
        }
        groupRepo.delete(groupRepo.findById( group.getId()).get());
        groupRepo.save(group);
        model.addAttribute("groups", groupRepo.findAll());
        return "group/all";
    }
}
