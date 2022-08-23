package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.student.site.StudentsAndGroupBoot.models.Student;

import java.util.List;


public interface StudentRepo extends JpaRepository<Student, Integer> {
    List<Student> findStudentByName(String searchWord);
    List<Student> findStudentBySurname(String searchWorld);
    List<Student> findStudentByGroupNumber(Integer id);
}
