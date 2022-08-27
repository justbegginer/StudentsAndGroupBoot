package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.student.site.StudentsAndGroupBoot.models.Student;

import java.util.List;


public interface StudentRepo extends JpaRepository<Student, Integer> {
    List<Student> findStudentByName(String searchWord);

    List<Student> findStudentBySurname(String searchWorld);

    List<Student> findStudentByGroupNumber(Integer id);
    @Query("select student from Student student where student.name like concat('%', ?1, '%')")
    List<Student> findStudentByIncludingInName(String searchWorld);

    @Query("select student from Student student where student.surname like concat('%', ?1, '%')")
    List<Student> findStudentByIncludingInSurname(String searchWorld);
}
