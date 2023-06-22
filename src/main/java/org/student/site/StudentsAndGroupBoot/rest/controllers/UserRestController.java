package org.student.site.StudentsAndGroupBoot.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.dto.PasswordsPair;
import org.student.site.StudentsAndGroupBoot.exceptions.IncorrectDataException;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.Status;
import org.student.site.StudentsAndGroupBoot.models.StatusPattern;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {

    private final UserServiceImpl userService;

    public UserRestController(@Autowired UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        if (userService.findAll().isEmpty()){
            throw new NotFoundException("There is no user");
        }
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        if (userService.findById(id).isEmpty()){
            throw new NotFoundException("There is no user with id " + id);
        }
        return userService.findById(id).get();
    }
    @PatchMapping("/{type}/{id}")
    public Status changePassword(@RequestBody PasswordsPair passwordsPair,
                                 @PathVariable("type") String type,
                                 @PathVariable("id") Integer id){

        Optional<User> user = userService.findTopByRoleAndUserId(type, id);
        if (user.isEmpty()){
            throw new NotFoundException("User not found");
        }
        if (!Objects.equals(userService.findTopByRoleAndUserId(type, id).get().getPassword(), passwordsPair.getOldPassword())) {
            throw new IncorrectDataException("Old password incorrect");
        }
        user.get().setPassword(passwordsPair.getNewPassword());
        userService.save(user.get());
        return new Status(true, StatusPattern.SUCCESS, null);
    }
}
