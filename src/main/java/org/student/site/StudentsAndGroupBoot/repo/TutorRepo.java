package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.Tutor;

import java.util.List;


public interface TutorRepo extends JpaRepository<Tutor, Integer> {
    List<Tutor> findTutorByName(String searchWord);

    List<Tutor> findTutorBySurname(String searchWorld);

    List<Tutor> findTutorByQualification(String searchWorld);

    @Query("select tutor from Tutor tutor where tutor.name like concat('%', ?1, '%')")
    List<Tutor> findTutorByIncludingInName(String searchWorld);

    @Query("select tutor from Tutor tutor where tutor.surname like concat('%', ?1, '%')")
    List<Tutor> findTutorByIncludingInSurname(String searchWorld);

    @Query("select tutor from Tutor tutor where tutor.qualification like concat('%', ?1, '%')")
    List<Tutor> findTutorByIncludingInQualification(String searchWorld);
}
