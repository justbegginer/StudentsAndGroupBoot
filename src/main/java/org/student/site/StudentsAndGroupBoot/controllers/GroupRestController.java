package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.Utils;
import org.student.site.StudentsAndGroupBoot.dto.GroupUserDto;
import org.student.site.StudentsAndGroupBoot.exceptions.IncorrectDataException;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.*;
import org.student.site.StudentsAndGroupBoot.repo.UserRepo;
import org.student.site.StudentsAndGroupBoot.services.impl.GroupServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.StudentServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.TutorServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/groups")
public class GroupRestController {

    private final UserServiceImpl userService;

    private final StudentServiceImpl studentService;

    private final TutorServiceImpl tutorService;

    private final GroupServiceImpl groupService;
    private final UserRepo userRepo;

    public GroupRestController(@Autowired UserServiceImpl userService,
                               @Autowired StudentServiceImpl studentService,
                               @Autowired TutorServiceImpl tutorService,
                               @Autowired GroupServiceImpl groupService,
                               UserRepo userRepo) {
        this.userService = userService;
        this.studentService = studentService;
        this.tutorService = tutorService;
        this.groupService = groupService;
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<Group> getAllGroups() {
        if (groupService.findAll().isEmpty()) {
            throw new NotFoundException("There is no one group");
        }
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable("id") int id) {
        if (groupService.findById(id).isEmpty()) {
            throw new NotFoundException("Group with id = " + id + " not found");
        }
        return groupService.findById(id).get();
    }

    @PostMapping()
    @Transactional
    public Status addNewGroupToDB(@RequestBody GroupUserDto groupUserDto) {
        groupService.save(groupUserDto.getGroup());
        User user = groupUserDto.getUser();
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
            userService.delete(userService.findTopByRoleAndUserId("group", id).get());
        } else {
            throw new NotFoundException("There is no such group with id = " + id);
        }
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @PatchMapping
    public Status updateGroup(@RequestBody @Valid Group group,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectDataException(Utils.getErrorStatusFromBindingResult(bindingResult));
        }
        groupService.save(group);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

}