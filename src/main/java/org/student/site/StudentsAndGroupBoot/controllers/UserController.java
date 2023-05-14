package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import javax.security.enterprise.credential.Password;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserServiceImpl userService;

    public UserController(@Autowired UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{type}/{id}")
    public String pageToChangePassword(Model model,
                                       @PathVariable("type") String type,
                                       @PathVariable("id") int id) {
        model.addAttribute("type", type);
        model.addAttribute("id", id);
        User newPasswords = new User();
        model.addAttribute("newPassword", newPasswords);
        model.addAttribute("oldPassword", newPasswords);
        return "/user/changePassword";
    }

    @PatchMapping("/{type}/{id}")
    public String changePassword(@ModelAttribute("oldPassword") User passwords,
                                 @PathVariable("type") String type,
                                 @PathVariable("id") int id) {
        if (!Objects.equals(userService.findTopByRoleAndUserId(type, id).getPassword(), passwords.getPassword().split(",")[0])) {
            return "redirect:/users/" + type + "/" + id;
        }
        User user = userService.findTopByRoleAndUserId(type, id);
        user.setPassword(passwords.getPassword().split(",")[1]);
        userService.save(user);
        return "redirect:/" + type + "/" + id;
    }
}
