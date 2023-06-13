package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.Utils;
import org.student.site.StudentsAndGroupBoot.models.*;
import org.student.site.StudentsAndGroupBoot.services.impl.TutorServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest/tutors")
public class TutorRestController {

    private final TutorServiceImpl tutorService;

    private final UserServiceImpl userService;

    public TutorRestController(@Autowired TutorServiceImpl tutorService,
                           @Autowired UserServiceImpl userService) {
        this.tutorService = tutorService;
        this.userService = userService;
    }

    @GetMapping
    public List<Tutor> getAllTutors() {
        return tutorService.findAll();
    }

    @GetMapping("/{id}")
    public Tutor getTutorById(@PathVariable("id") int id) {
        Optional<Tutor> optionalTutor = tutorService.findById(id);
        if (optionalTutor.isEmpty()) {
            // TODO error 404
        }
        return optionalTutor.get();
    }

    @PostMapping()
    @Transactional
    public Status addNewTutorToDB(@RequestBody Map<String, Map<String, String>> request) {
        Tutor tutor = new Tutor();
        tutor.setName(request.get("tutor").get("name"));
        tutor.setSurname(request.get("tutor").get("surname"));
        tutor.setQualification(request.get("tutor").get("qualification"));
        tutorService.save(tutor);
        User user = new User();
        user.setEmail(request.get("user").get("email"));
        user.setPassword(request.get("user").get("password"));
        user.setRole("tutor");
        user.setUserId(tutorService.findTopByOrderByIdDesc().getId());
        user.setLoginBasedOnEmail();
        userService.save(user);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @DeleteMapping("{id}")
    @Transactional
    public Status deleteTutorFromDB(@PathVariable("id") int id) {
        Optional<Tutor> optionalTutor = tutorService.findById(id);
        if (optionalTutor.isEmpty()){
            return new Status(false, StatusPattern.NOT_FOUND,
                    "Tutor with id " + id + " does not exist");
        }
        tutorService.delete(optionalTutor.get());
        userService.delete(userService.findTopByRoleAndUserId("tutor", id));
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @PatchMapping("{id}")
    public Status updateGroup(@RequestBody @Valid Tutor tutor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Utils.getErrorStatusFromBindingResult(bindingResult);
        }
        if (tutorService.findById(tutor.getId()).isEmpty()){
            return new Status(false, StatusPattern.NOT_FOUND,
                    "Tutor with id = " + tutor.getId() + " doesn't exist'");
        }
        tutorService.save(tutor);
        return new Status(true, StatusPattern.SUCCESS, null);
    }
}