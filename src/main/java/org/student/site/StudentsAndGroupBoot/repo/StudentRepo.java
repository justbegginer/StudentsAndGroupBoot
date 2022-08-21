package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.student.site.StudentsAndGroupBoot.models.Student;


public interface StudentRepo extends JpaRepository<Student, Integer> {
}
