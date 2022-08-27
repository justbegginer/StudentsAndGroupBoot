package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.models.CompleteGroup;
import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.Tutor;
import org.student.site.StudentsAndGroupBoot.repo.GroupRepo;
import org.student.site.StudentsAndGroupBoot.repo.StudentRepo;
import org.student.site.StudentsAndGroupBoot.repo.TutorRepo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    public String getAllGroups(Model model,
                               @RequestParam(value = "fullInfo", required = false) boolean fullInfo) {
        if (fullInfo) {
            List<Group> groupList = groupRepo.findAll();
            List<CompleteGroup> completeGroups = new ArrayList<>(groupList.size());
            for (int i = 0; i < groupList.size(); i++) {
                Group temp = groupList.get(i);
                completeGroups.add(new CompleteGroup(temp,
                        tutorRepo.findById(temp.getTutorId()).get(), studentRepo.findStudentByGroupNumber(temp.getId())));
            }
            model.addAttribute("completeGroupsList", completeGroups);
            return "group/allFullInfo";
        } else {
            model.addAttribute("groups", groupRepo.findAll());
            return "group/all";
        }
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
        groupRepo.save(group);
        model.addAttribute("groups", groupRepo.findAll());
        return "group/all";
    }

    @GetMapping("/addStudent/{id}")
    public String chooseStudentToAdd(@PathVariable("id") int id, Model model){
        model.addAttribute("students", studentRepo.findAll());
        model.addAttribute("id", id);
        model.addAttribute("student1", new Student());
        return "student/listToAddByClick";
    }

    @PostMapping("/addStudent/{id}")
    public String addStudentToGroup(@PathVariable("id") int id,
                                    @ModelAttribute("student")Student student){
        student.setGroupNumber(id);
        studentRepo.save(student);
        return "redirect:/groups/"+id+"?fullInfo=true";
    }

    @PatchMapping("/addStudent/{groupId}/{studentId}")
    public String linkStudentToGroup(@PathVariable("groupId") int groupId,
                                     @PathVariable("studentId") int studentId){
        Student student =  studentRepo.findById(studentId).get();
        student.setGroupNumber(groupId);
        studentRepo.save(student);
        return "redirect:/groups/"+groupId+"?fullInfo=true";
    }

    @GetMapping("/addTutor/{id}")
    public String chooseTutorToAdd(@PathVariable("id") int id, Model model){
        model.addAttribute("tutors", tutorRepo.findAll());
        model.addAttribute("id", id);
        model.addAttribute("tutor1", new Tutor());
        return "tutor/listToAddByClick";
    }

    @PostMapping("/addTutor/{id}")
    public String addTutorToGroup(@PathVariable("id") int id,
                                    @ModelAttribute("tutor")Tutor tutor){
        tutor.setId(0);
        Group group = groupRepo.findById(id).get();
        group.setTutorId(tutor.getId());
        groupRepo.save(group);
        tutorRepo.save(tutor);
        return "redirect:/groups/"+id+"?fullInfo=true";
    }

    @PatchMapping("/addTutor/{groupId}/{studentId}")
    public String linkTutorToGroup(@PathVariable("groupId") int groupId,
                                     @PathVariable("studentId") int tutorId){
        Group group = groupRepo.findById(groupId).get();
        group.setTutorId(tutorId);
        groupRepo.save(group);
        return "redirect:/groups/"+groupId+"?fullInfo=true";
    }

}
