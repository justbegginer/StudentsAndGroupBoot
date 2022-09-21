package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.Tutor;
import org.student.site.StudentsAndGroupBoot.repo.GroupRepo;
import org.student.site.StudentsAndGroupBoot.repo.StudentRepo;
import org.student.site.StudentsAndGroupBoot.repo.TutorRepo;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private GroupRepo groupRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private TutorRepo tutorRepo;

    @GetMapping("/")
    public String mainPage() {
        return "startPage";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "word", required = false) String word) {
        try {
            int id = Integer.parseInt(word);
            if (groupRepo.findById(id).isPresent())
                model.addAttribute("groups", groupRepo.findById(id).get());
            if (studentRepo.findById(id).isPresent())
                model.addAttribute("students", studentRepo.findById(id).get());
            if (tutorRepo.findById(id).isPresent())
                model.addAttribute("tutors", tutorRepo.findById(id).get());
            model.addAttribute("word", id);
        } catch (NumberFormatException exception) {
            List<Student> studentList = studentRepo.findStudentByIncludingInName(word);
            studentList.addAll(studentRepo.findStudentByIncludingInSurname(word));
            model.addAttribute("students", studentList);
            List<Tutor> tutorList = tutorRepo.findTutorByIncludingInName(word);
            tutorList.addAll(tutorRepo.findTutorByIncludingInSurname(word));
            tutorList.addAll(tutorRepo.findTutorByIncludingInQualification(word));
            model.addAttribute("tutors", tutorList);
            model.addAttribute("word", word);
        }
        return "search";
    }
}
