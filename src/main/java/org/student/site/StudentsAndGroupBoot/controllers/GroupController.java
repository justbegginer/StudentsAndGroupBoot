package org.student.site.StudentsAndGroupBoot.controllers;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.models.*;
import org.student.site.StudentsAndGroupBoot.services.impl.GroupServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.StudentServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.TutorServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private TutorServiceImpl tutorService;

    @Autowired
    private GroupServiceImpl groupService;

    @GetMapping
    public String getAllGroups(Model model,
                               @RequestParam(value = "fullInfo", required = false) boolean fullInfo) {
        if (fullInfo) {
            List<Group> groupList = groupService.findAll();
            List<CompleteGroup> completeGroups = new ArrayList<>(groupList.size());
            for (int i = 0; i < groupList.size(); i++) {
                Group temp = groupList.get(i);
                completeGroups.add(new CompleteGroup(temp,
                        tutorService.findById(temp.getTutorId()).get(), studentService.findStudentByGroupNumber(temp.getId())));
            }
            model.addAttribute("completeGroupsList", completeGroups);
            return "group/allFullInfo";
        } else {
            model.addAttribute("groups", groupService.findAll());
            return "group/all";
        }
    }

    @GetMapping("/{id}")
    public String getGroupById(@PathVariable("id") int id, Model model,
                               @RequestParam(value = "fullinfo", required = false) boolean fullInfo) {
        if (groupService.findById(id).isEmpty()) {
            model.addAttribute("message", "Group with id = " + id + " not found");
            return "errors/error404";
        }
        if (!fullInfo) {
            model.addAttribute("group", groupService.findById(id).get());
            return "group/getGroup";
        } else {
            CompleteGroup completeGroup = new CompleteGroup(groupService.findById(id).get(),
                    tutorService.findById(groupService.findById(id).get().getTutorId()).get(),
                    studentService.findStudentByGroupNumber(id));
            model.addAttribute("completeGroup", completeGroup);
            return "group/getGroupWithAllInfo";
        }
    }

    @GetMapping("/new")
    public String newGroup(Model model) {
        model.addAttribute("group", new Group());
        model.addAttribute("user", new User());
        return "group/add";
    }

    @PostMapping()
    public String addNewGroupToDB(@ModelAttribute("group") @Valid Group group,
                                  BindingResult bindingResult,
                                  @ModelAttribute("user") User user) {
        if (bindingResult.hasErrors()) {
            return "group/add";
        }
        groupService.save(group);
        user.setRole("group");
        user.setUserId(groupService.findTopByOrderByIdDesc().getId());
        user.setLoginBasedOnEmail();
        userService.save(user);
        return "redirect:/groups";
    }

    @GetMapping("{id}/delete")
    public String pageToDelete(@PathVariable("id") int id, Model model) {
        if (groupService.findById(id).isEmpty()) {
            model.addAttribute("message", "Group with id = " + id + " not found");
            return "error404";
        }
        model.addAttribute("group", groupService.findById(id).get());
        return "group/delete";
    }

    @DeleteMapping("{id}")
    public String deleteGroupFromDB(@PathVariable("id") int id) {
        groupService.delete(groupService.findById(id).get());
        return "redirect:/groups";
    }

    @GetMapping("{id}/update")
    public String pageToUpdate(@PathVariable("id") int id, Model model) {
        if (groupService.findById(id).isEmpty()) {
            model.addAttribute("message", "Group with id = " + id + " not found");
            return "error404";
        }
        model.addAttribute("group", groupService.findById(id).get());
        return "group/update";
    }

    @PatchMapping("{id}")
    public String updateGroup(@ModelAttribute("group") @Valid Group group,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "group/update";
        }
        groupService.save(group);
        model.addAttribute("groups", groupService.findAll());
        return "group/all";
    }

    @GetMapping("/addStudent/{id}")
    public String chooseStudentToAdd(@PathVariable("id") int id, Model model) {
        model.addAttribute("students", studentService.findStudentWhichNotInGroup(id));
        model.addAttribute("id", id);
        model.addAttribute("student1", new Student());
        return "student/listToAddByClick";
    }

    @PostMapping("/addStudent/{id}")
    public String addStudentToGroup(@PathVariable("id") int id,
                                    @ModelAttribute("student") @Valid Student student) {
        student.setGroupNumber(id);
        studentService.save(student);
        return "redirect:/groups/" + id + "?fullInfo=true";
    }

    @GetMapping("/removeStudent/{id}")
    public String chooseStudentToRemove(@PathVariable("id") int id, Model model){
        model.addAttribute("students", studentService.findStudentByGroupNumber(id));
        model.addAttribute("id", id);
        return "student/listToRemoveByClick";
    }
    @PatchMapping("/removeStudent/{id}/{studentId}")
    public String removeStudentFromGroup(@PathVariable("id") int id,
                                         @PathVariable("studentId") int studentId){
        Student student = studentService.findById(studentId).get();
        student.setGroupNumber(100); // TODO FIX number
        studentService.save(student);
        return "redirect:/groups/"+id+"?fullInfo=true";
    }

    @PatchMapping("/addStudent/{groupId}/{studentId}")
    public String linkStudentToGroup(@PathVariable("groupId") int groupId,
                                     @PathVariable("studentId") int studentId) {
        Student student = studentService.findById(studentId).get();
        student.setGroupNumber(groupId);
        studentService.save(student);
        return "redirect:/groups/" + groupId + "?fullInfo=true";
    }

    @GetMapping("/addTutor/{id}")
    public String chooseTutorToAdd(@PathVariable("id") int id, Model model) {
        model.addAttribute("tutors", tutorService.findAll());
        model.addAttribute("id", id);
        model.addAttribute("tutor1", new Tutor());
        return "tutor/listToAddByClick";
    }

    @PostMapping("/addTutor/{id}")
    public String addTutorToGroup(@PathVariable("id") int id,
                                  @ModelAttribute("tutor") @Valid Tutor tutor) {
        tutor.setId(0);
        Group group = groupService.findById(id).get();
        group.setTutorId(tutor.getId());
        groupService.save(group);
        tutorService.save(tutor);
        return "redirect:/groups/" + id + "?fullInfo=true";
    }

    @PatchMapping("/addTutor/{groupId}/{studentId}")
    public String linkTutorToGroup(@PathVariable("groupId") int groupId,
                                   @PathVariable("studentId") int tutorId) {
        Group group = groupService.findById(groupId).get();
        group.setTutorId(tutorId);
        groupService.save(group);
        return "redirect:/groups/" + groupId + "?fullInfo=true";
    }

}
