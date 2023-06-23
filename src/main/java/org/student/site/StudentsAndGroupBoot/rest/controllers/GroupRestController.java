package org.student.site.StudentsAndGroupBoot.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.dto.GroupUserDto;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.*;
import org.student.site.StudentsAndGroupBoot.repo.UserRepo;
import org.student.site.StudentsAndGroupBoot.services.impl.GroupServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.StudentServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.TutorServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/groups")
public class GroupRestController {

    private final UserServiceImpl userService;

    private final GroupServiceImpl groupService;

    public GroupRestController(@Autowired UserServiceImpl userService,
                               @Autowired GroupServiceImpl groupService) {
        this.userService = userService;
        this.groupService = groupService;
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
    public Status updateGroup(@RequestBody Group group) {
        if (groupService.findById(group.getId()).isEmpty()){
            throw new NotFoundException("There is no such group with id = " + group.getId());
        }
        groupService.save(group);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

}