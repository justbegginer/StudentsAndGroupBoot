package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.student.site.StudentsAndGroupBoot.models.Tutor;

import java.util.List;


public interface TutorRepo extends JpaRepository<Tutor, Integer> {
    List<Tutor> findTutorByName(String searchWord);

    List<Tutor> findTutorBySurname(String searchWorld);

    List<Tutor> findTutorByQualification(String searchWorld);
}
