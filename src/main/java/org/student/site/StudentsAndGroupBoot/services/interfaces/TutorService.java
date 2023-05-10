package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.Tutor;

import java.util.List;
import java.util.Optional;

public interface TutorService {

    Optional<Tutor> findById(Integer id);

    List<Tutor> findAll();

    List<Tutor> update();

    void save(Tutor tutor);

    void delete(Tutor tutor);

    List<Tutor> findTutorByName(String searchWord);

    List<Tutor> findTutorBySurname(String searchWorld);

    List<Tutor> findTutorByQualification(String searchWorld);

    List<Tutor> findTutorByIncludingInName(String searchWorld);

    List<Tutor> findTutorByIncludingInSurname(String searchWorld);

    List<Tutor> findTutorByIncludingInQualification(String searchWorld);

    List<Tutor> findTutorByIncludingInNameSurnameAndQualification(String searchWorld1, String searchWorld2, String searchWorld3);

    Tutor findTopByOrderByIdDesc();
}
