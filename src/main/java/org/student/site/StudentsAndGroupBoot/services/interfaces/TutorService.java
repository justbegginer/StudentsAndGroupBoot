package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.student.site.StudentsAndGroupBoot.models.Tutor;
import org.student.site.StudentsAndGroupBoot.models.User;

import java.util.List;
import java.util.Optional;

public interface TutorService extends Service<Tutor> {

    @Override
    Optional<Tutor> findById(Integer id);

    @Override
    List<Tutor> findAll();

    @Override
    void save(Tutor tutor, User user);

    @Override
    void delete(Integer id);

    @Override
    void update(Tutor tutor);

    List<Tutor> findTutorByName(String searchWord);

    List<Tutor> findTutorBySurname(String searchWorld);

    List<Tutor> findTutorByQualification(String searchWorld);

    List<Tutor> findTutorByIncludingInName(String searchWorld);

    List<Tutor> findTutorByIncludingInSurname(String searchWorld);

    List<Tutor> findTutorByIncludingInQualification(String searchWorld);

    List<Tutor> findTutorByIncludingInNameSurnameAndQualification(String searchWorld1, String searchWorld2, String searchWorld3);

    Tutor findTopByOrderByIdDesc();
}
