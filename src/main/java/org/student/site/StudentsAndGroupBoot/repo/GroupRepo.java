package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.student.site.StudentsAndGroupBoot.models.Group;

public interface GroupRepo extends JpaRepository<Group, Integer> {
}
