package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.student.site.StudentsAndGroupBoot.models.Tutor;

import java.util.List;

@Repository
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

    @Query("select tutor from Tutor tutor " +
            "where (tutor.name like concat('%', ?1, '%') and  tutor.surname like concat('%', ?2, '%') and tutor.qualification like concat('%', ?3, '%'))" +
            "or (tutor.name like concat('%', ?1, '%') and  tutor.surname like concat('%', ?3, '%') and tutor.qualification like concat('%', ?2, '%')) " +
            "or (tutor.name like concat('%', ?2, '%') and  tutor.surname like concat('%', ?1, '%') and tutor.qualification like concat('%', ?3, '%')) " +
            "or (tutor.name like concat('%', ?2, '%') and  tutor.surname like concat('%', ?3, '%') and tutor.qualification like concat('%', ?1, '%')) " +
            "or (tutor.name like concat('%', ?3, '%') and  tutor.surname like concat('%', ?1, '%') and tutor.qualification like concat('%', ?2, '%')) " +
            "or (tutor.name like concat('%', ?3, '%') and  tutor.surname like concat('%', ?2, '%') and tutor.qualification like concat('%', ?1, '%'))")
    List<Tutor> findTutorByIncludingInNameSurnameAndQualification(String searchWorld1, String searchWorld2, String searchWorld3);
}
