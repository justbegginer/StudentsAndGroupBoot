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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
            if (word != null) {
                model.addAttribute("students", resultOfStudentSearch(word));
            }
            if (word != null) {
                model.addAttribute("tutors", resultOfTutorSearch(word));
            }
            model.addAttribute("word", word);
        }
        return "search";
    }

    private List<Student> resultOfStudentSearch(String request) {
        String[] words = request.split(" ");

        if (words.length == 2) {
            System.out.println(request);
            List<Student> studentList = studentRepo.findStudentByIncludingInNameAndSurname(words[0], words[1]);
            Collections.sort(studentList, compareStudents());
            studentList.addAll(studentRepo.findStudentByPartlyIncludingInNameAndSurname(words[0], words[1]));
            Collections.sort(studentList, compareStudents());
            return studentList.stream()
                    .distinct()
                    .collect(Collectors.toList());
        } else if (words.length == 1) {
            List<Student> studentList = studentRepo.findStudentByIncludingInName(request);
            studentList.addAll(studentRepo.findStudentByIncludingInSurname(request));
            Collections.sort(studentList, compareStudents());
            return studentList;
        } else {
            return new ArrayList<>();
        }
    }

    private List<Tutor> resultOfTutorSearch(String request){
        String[] words = request.split(" ");
        if (words.length == 3){
            List<Tutor> tutorList = tutorRepo.findTutorByIncludingInNameSurnameAndQualification(words[0], words[1], words[2]);
            Collections.sort(tutorList, compareTutor());
            System.out.println(tutorList.size());
            return tutorList;
        }
        else if (words.length == 1){
            List<Tutor> tutorList = tutorRepo.findTutorByIncludingInName(request);
            tutorList.addAll(tutorRepo.findTutorByIncludingInSurname(request));
            tutorList.addAll(tutorRepo.findTutorByIncludingInQualification(request));
            Collections.sort(tutorList, compareTutor());
            return tutorList;
        }
        else {
            return new ArrayList<>();
        }
    }

    private Comparator<Tutor> compareTutor(){
        return (lhs, rhs) -> {
            String rightName = rhs.getName(), rightSurname = rhs.getSurname();
            String leftName = lhs.getName(), leftSurname = lhs.getSurname();
            for (int i = 0; i < rightName.length() && i < leftName.length(); i++) {
                if (rightName.charAt(i) < leftName.charAt(i)) {
                    return 1;
                } else if (rightName.charAt(i) > leftName.charAt(i)) {
                    return -1;
                }
            }
            if (rightName.length() > leftName.length()) {
                return -1;
            } else if (rightName.length() < leftName.length()) {
                return 1;
            }
            for (int i = 0; i < rightSurname.length() && i < leftSurname.length(); i++) {
                if (rightSurname.charAt(i) < leftSurname.charAt(i)) {
                    return 1;
                } else if (rightSurname.charAt(i) > leftSurname.charAt(i)) {
                    return -1;
                }
            }
            if (rightSurname.length() > leftSurname.length()) {
                return -1;
            } else if (rightSurname.length() < leftSurname.length()) {
                return 1;
            } else {
                return 0;
            }
        };
    }
    private Comparator<Student> compareStudents() {
        return (lhs, rhs) -> {
            String rightName = rhs.getName(), rightSurname = rhs.getSurname();
            String leftName = lhs.getName(), leftSurname = lhs.getSurname();
            for (int i = 0; i < rightName.length() && i < leftName.length(); i++) {
                if (rightName.charAt(i) < leftName.charAt(i)) {
                    return 1;
                } else if (rightName.charAt(i) > leftName.charAt(i)) {
                    return -1;
                }
            }
            if (rightName.length() > leftName.length()) {
                return -1;
            } else if (rightName.length() < leftName.length()) {
                return 1;
            }
            for (int i = 0; i < rightSurname.length() && i < leftSurname.length(); i++) {
                if (rightSurname.charAt(i) < leftSurname.charAt(i)) {
                    return 1;
                } else if (rightSurname.charAt(i) > leftSurname.charAt(i)) {
                    return -1;
                }
            }
            if (rightSurname.length() > leftSurname.length()) {
                return -1;
            } else if (rightSurname.length() < leftSurname.length()) {
                return 1;
            } else {
                return 0;
            }
        };
    }
}
