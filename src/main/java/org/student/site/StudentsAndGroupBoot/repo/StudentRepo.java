package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.student.site.StudentsAndGroupBoot.models.Student;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    List<Student> findStudentByName(String searchWord);

    List<Student> findStudentBySurname(String searchWorld);

    List<Student> findStudentByGroupNumber(Integer id);
    @Query("select student from Student student where student.name like concat('%', ?1, '%')")
    List<Student> findStudentByIncludingInName(String searchWorld);

    @Query("select student from Student student where student.surname like concat('%', ?1, '%')")
    List<Student> findStudentByIncludingInSurname(String searchWorld);

    @Query("select student from Student student " +
            "where (student.name = ?1 and student.surname = ?2) or (student.name = ?2 and student.surname = ?1)")
    List<Student> findStudentByIncludingInNameAndSurname(String searchWord1, String searchWord2);

    @Query("select student from Student student " +
            "where (student.name like concat('%', ?1, '%') and student.surname like concat('%', ?2, '%')) " +
            "or (student.name like concat('%', ?2, '%') and student.surname like concat('%', ?1, '%'))")
    List<Student> findStudentByPartlyIncludingInNameAndSurname(String searchWorld1, String searchWorld2);
}
