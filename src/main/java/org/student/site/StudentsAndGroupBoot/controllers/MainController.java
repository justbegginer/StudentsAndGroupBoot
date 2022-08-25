package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        System.out.println("working");
        try {
            System.out.println("id " + word + "?");
            int id = Integer.parseInt(word);
            model.addAttribute("groups", groupRepo.findById(id).get());
            model.addAttribute("students", studentRepo.findById(id).get());
            model.addAttribute("tutors", tutorRepo.findById(id).get());
            model.addAttribute("word", id);
        } catch (NumberFormatException exception) {
            System.out.println("catching word");
            List<Student> studentList = studentRepo.findStudentByName(word);
            studentList.addAll(studentRepo.findStudentBySurname(word));
            model.addAttribute("students", studentList);
            List<Tutor> tutorList = tutorRepo.findTutorByName(word);
            tutorList.addAll(tutorRepo.findTutorBySurname(word));
            tutorList.addAll(tutorRepo.findTutorByQualification(word));
            model.addAttribute("tutors", tutorList);
            model.addAttribute("word", word);
        }
        return "search";
    }
}
