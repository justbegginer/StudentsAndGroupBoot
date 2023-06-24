package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.User;

import java.util.List;
import java.util.Optional;

public interface StudentService extends Service<Student> {

    @Override
    Optional<Student> findById(Integer id);

    @Override
    List<Student> findAll();

    @Override
    void save(Student student, User user);

    @Override
    void delete(Integer id);

    @Override
    void update(Student student);

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
