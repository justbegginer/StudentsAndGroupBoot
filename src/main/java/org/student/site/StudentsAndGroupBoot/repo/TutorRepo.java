package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.student.site.StudentsAndGroupBoot.models.Tutor;


public interface TutorRepo extends JpaRepository<Tutor, Integer> {
}
