package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.Utils;
import org.student.site.StudentsAndGroupBoot.models.*;
import org.student.site.StudentsAndGroupBoot.services.impl.GroupServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.StudentServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.TutorServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest/groups")
public class GroupRestController {

    private final UserServiceImpl userService;

    private final StudentServiceImpl studentService;

    private final TutorServiceImpl tutorService;

    private final GroupServiceImpl groupService;

    public GroupRestController(@Autowired UserServiceImpl userService,
                           @Autowired StudentServiceImpl studentService,
                           @Autowired TutorServiceImpl tutorService,
                           @Autowired GroupServiceImpl groupService) {
        this.userService = userService;
        this.studentService = studentService;
        this.tutorService = tutorService;
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable("id") int id) {
        return groupService.findById(id).get();
    }

    @PostMapping()
    @Transactional
    public Status addNewGroupToDB(@RequestBody Map<String, Map<String,String>> map) {
        Group group = new Group();
        group.setTutorId(Integer.parseInt(map.get("group").get("tutorId")));
        groupService.save(group);
        User user = new User();
        user.setEmail(map.get("user").get("email"));
        user.setPassword(map.get("user").get("password"));
        user.setRole("group");
        user.setUserId(groupService.findTopByOrderByIdDesc().getId());
        user.setLoginBasedOnEmail();
        userService.save(user);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @DeleteMapping("{id}")
    @Transactional
    public Status deleteGroupFromDB(@PathVariable("id") int id) {
        Optional<Group> group = groupService.findById(id);
        if (group.isPresent()) {
            groupService.delete(group.get());
            userService.delete(userService.findTopByRoleAndUserId("group", id));
            return new Status(true, StatusPattern.SUCCESS, null);
        } else {
            return new Status(false, StatusPattern.NOT_FOUND, "There is no such group with id = " + id);
        }
    }

    @PatchMapping
    public Status updateGroup(@RequestBody @Valid Group group,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Utils.getErrorStatusFromBindingResult(bindingResult);
        }
        groupService.save(group);
        return new Status(true, StatusPattern.SUCCESS, null);
    }


    @PostMapping("/addStudent/{id}")
    public Status addStudentToGroup(@PathVariable("id") int id,
                                    @RequestBody @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Utils.getErrorStatusFromBindingResult(bindingResult);
        }
        if (groupService.findById(id).isEmpty()) {
            return new Status(false, StatusPattern.NOT_FOUND, "There is no student with id = " + id);
        }
        student.setGroupNumber(id);
        studentService.save(student);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @PatchMapping("/removeStudent/{id}/{studentId}")
    public Status removeStudentFromGroup(@PathVariable("id") int id,
                                         @PathVariable("studentId") int studentId) {
        Optional<Student> student = studentService.findById(studentId);
        if (student.isEmpty()) {
            return new Status(false, StatusPattern.NOT_FOUND, "There is no student with id = " + id);
        }
        student.get().setGroupNumber(null);
        studentService.save(student.get());
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @PatchMapping("/addStudent/{groupId}/{studentId}")
    public Status linkStudentToGroup(@PathVariable("groupId") int groupId,
                                     @PathVariable("studentId") int studentId) {
        if (studentService.findById(studentId).isEmpty()) {
            return new Status(false, StatusPattern.NOT_FOUND, "There is no student with id = " + studentId);
        }
        if (groupService.findById(groupId).isEmpty()) {
            return new Status(false, StatusPattern.NOT_FOUND, "There is no group with id = " + groupId);
        }
        Student student = studentService.findById(studentId).get();
        student.setGroupNumber(groupId);
        studentService.save(student);
        return new Status(true, StatusPattern.SUCCESS, null);
    }


    @PostMapping("/addTutor/{id}")
    @Transactional
    public Status addTutorToGroup(@PathVariable("id") int id,
                                  @RequestBody @Valid Tutor tutor, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return Utils.getErrorStatusFromBindingResult(bindingResult);
        }
        tutor.setId(0); // TODO why
        Optional<Group> group = groupService.findById(id);
        if (group.isEmpty()) {
            return new Status(false, StatusPattern.NOT_FOUND, "There is no group with id = " + id);
        }
        group.get().setTutorId(tutor.getId());
        groupService.save(group.get());
        tutorService.save(tutor);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @PatchMapping("/addTutor/{groupId}/{tutorId}")
    public Status linkTutorToGroup(@PathVariable("groupId") int groupId,
                                   @PathVariable("tutorId") int tutorId) {

        Optional<Group> group = groupService.findById(groupId);
        if (group.isEmpty()) {
            return new Status(false, StatusPattern.NOT_FOUND, "There is no group with id = " + groupId);
        }
        if (tutorService.findById(tutorId).isEmpty()) {
            return new Status(false, StatusPattern.NOT_FOUND, "There is no tutor with id = " + tutorId);
        }
        group.get().setTutorId(tutorId);
        groupService.save(group.get());
        return new Status(true, StatusPattern.SUCCESS, null);
    }

}