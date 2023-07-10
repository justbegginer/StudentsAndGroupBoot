package org.student.site.StudentsAndGroupBoot.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.student.site.StudentsAndGroupBoot.dto.TutorUserDto;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.*;
import org.student.site.StudentsAndGroupBoot.services.impl.TutorServiceImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/tutors")
public class TutorRestController {

    private final TutorServiceImpl tutorService;

    public TutorRestController(@Autowired TutorServiceImpl tutorService) {
        this.tutorService = tutorService;
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
        return tutorService.findById(id).get();
    }

    @PostMapping()
    public Status addNewTutorToDB(@RequestBody TutorUserDto tutorUserDto) {
        tutorService.save(tutorUserDto.getTutor(), tutorUserDto.getUser());
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @DeleteMapping("{id}")
    public Status deleteTutorFromDB(@PathVariable("id") int id) {
        tutorService.delete(id);
        return new Status(true, StatusPattern.SUCCESS, null);
    }

    @PatchMapping
    public Status updateGroup(@RequestBody Tutor tutor) {
        tutorService.update(tutor);
        return new Status(true, StatusPattern.SUCCESS, null);
    }
}