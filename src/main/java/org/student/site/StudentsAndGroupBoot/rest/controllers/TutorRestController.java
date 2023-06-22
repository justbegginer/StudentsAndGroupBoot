package org.student.site.StudentsAndGroupBoot.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.Utils;
import org.student.site.StudentsAndGroupBoot.dto.TutorUserDto;
import org.student.site.StudentsAndGroupBoot.exceptions.IncorrectDataException;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.*;
import org.student.site.StudentsAndGroupBoot.services.impl.TutorServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
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
        if (tutorService.findAll().isEmpty()) {
            throw new NotFoundException("There is no one tutors");
        }
        return tutorService.findAll();
    }

    @GetMapping("/{id}")
    public Tutor getTutorById(@PathVariable("id") int id) {
        Optional<Tutor> optionalTutor = tutorService.findById(id);
        if (optionalTutor.isEmpty()) {
            throw new NotFoundException("Tutor with id = " + id + " not found");
        }
        return optionalTutor.get();
    }

    @PostMapping()
    @Transactional
    public Status addNewTutorToDB(@RequestBody TutorUserDto tutorUserDto) {
        tutorService.save(tutorUserDto.getTutor());
        User user = tutorUserDto.getUser();
        user.setRole("tutor");
        user.setUserId(tutorService.findTopByOrderByIdDesc().getId());
        userService.save(user);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @DeleteMapping("{id}")
    @Transactional
    public Status deleteTutorFromDB(@PathVariable("id") int id) {
        Optional<Tutor> optionalTutor = tutorService.findById(id);
        if (optionalTutor.isEmpty()) {
            throw new NotFoundException("Tutor with id " + id + " does not exist");
        }
        tutorService.delete(optionalTutor.get());
        userService.delete(userService.findTopByRoleAndUserId("tutor", id).get());
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @PatchMapping
    public Status updateGroup(@RequestBody Tutor tutor) {
        if (tutorService.findById(tutor.getId()).isEmpty()) {
            throw new NotFoundException("Tutor with id = " + tutor.getId() + " doesn't exist'");
        }
        tutorService.save(tutor);
        return new Status(true, StatusPattern.SUCCESS, null);
    }
}