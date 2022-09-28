package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.Tutor;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Optional<Student> findById(Integer id);

    List<Student> findAll();

    void save(Student student);

    void delete(Student student);

    List<Student> findStudentByName(String searchWord);

    List<Student> findStudentBySurname(String searchWorld);

    List<Student> findStudentByGroupNumber(Integer id);

    List<Student> findStudentByIncludingInName(String searchWorld);

    List<Student> findStudentByIncludingInSurname(String searchWorld);

    List<Student> findStudentByIncludingInNameAndSurname(String searchWord1, String searchWord2);

    List<Student> findStudentByPartlyIncludingInNameAndSurname(String searchWorld1, String searchWorld2);

    List<Student> findStudentWhichNotInGroup(Integer id);

    Student findTopByOrderByIdDesc();
}
