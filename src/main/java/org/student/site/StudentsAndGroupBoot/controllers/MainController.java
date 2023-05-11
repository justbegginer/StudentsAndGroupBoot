package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.Tutor;
import org.student.site.StudentsAndGroupBoot.services.impl.GroupServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.StudentServiceImpl;
import org.student.site.StudentsAndGroupBoot.services.impl.TutorServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private GroupServiceImpl groupService;

    private StudentServiceImpl studentService;

    private TutorServiceImpl tutorService;

    public MainController(@Autowired GroupServiceImpl groupService,
                          @Autowired StudentServiceImpl studentService,
                          @Autowired TutorServiceImpl tutorService) {
        this.groupService = groupService;
        this.studentService = studentService;
        this.tutorService = tutorService;
    }

    @GetMapping("/")
    public String mainPage() {
        return "startPage";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "word", required = false) String word) {
        try {
            int id = Integer.parseInt(word);
            if (groupService.findById(id).isPresent())
                model.addAttribute("groups", groupService.findById(id).get());
            if (studentService.findById(id).isPresent())
                model.addAttribute("students", studentService.findById(id).get());
            if (tutorService.findById(id).isPresent())
                model.addAttribute("tutors", tutorService.findById(id).get());
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
            List<Student> studentList = studentService.findStudentByIncludingInNameAndSurname(words[0], words[1]);
            Collections.sort(studentList, compareStudents());
            studentList.addAll(studentService.findStudentByPartlyIncludingInNameAndSurname(words[0], words[1]));
            Collections.sort(studentList, compareStudents());
            return studentList.stream()
                    .distinct()
                    .collect(Collectors.toList());
        } else if (words.length == 1) {
            List<Student> studentList = studentService.findStudentByIncludingInName(request);
            studentList.addAll(studentService.findStudentByIncludingInSurname(request));
            Collections.sort(studentList, compareStudents());
            return studentList;
        } else {
            return new ArrayList<>();
        }
    }

    private List<Tutor> resultOfTutorSearch(String request) {
        String[] words = request.split(" ");
        if (words.length == 3) {
            List<Tutor> tutorList = tutorService.findTutorByIncludingInNameSurnameAndQualification(words[0], words[1], words[2]);
            Collections.sort(tutorList, compareTutor());
            return tutorList;
        } else if (words.length == 1) {
            List<Tutor> tutorList = tutorService.findTutorByIncludingInName(request);
            tutorList.addAll(tutorService.findTutorByIncludingInSurname(request));
            tutorList.addAll(tutorService.findTutorByIncludingInQualification(request));
            Collections.sort(tutorList, compareTutor());
            return tutorList.stream()
                    .distinct()
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    private Comparator<Tutor> compareTutor() {
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
