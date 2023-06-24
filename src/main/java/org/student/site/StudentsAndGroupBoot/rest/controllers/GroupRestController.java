package org.student.site.StudentsAndGroupBoot.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.dto.GroupUserDto;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.*;
import org.student.site.StudentsAndGroupBoot.services.impl.GroupServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/rest/groups")
public class GroupRestController {

    private final GroupServiceImpl groupService;

    public GroupRestController(@Autowired GroupServiceImpl groupService) {
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
    public Status addNewGroupToDB(@RequestBody GroupUserDto groupUserDto) {
        groupService.save(groupUserDto.getGroup(), groupUserDto.getUser());
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @DeleteMapping("{id}")
    public Status deleteGroupFromDB(@PathVariable("id") int id) {
        groupService.delete(id);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @PatchMapping
    public Status updateGroup(@RequestBody Group group) {
        groupService.update(group);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

}