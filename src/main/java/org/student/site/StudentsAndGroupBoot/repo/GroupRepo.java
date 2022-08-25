package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.student.site.StudentsAndGroupBoot.models.Group;

import java.util.List;

public interface GroupRepo extends JpaRepository<Group, Integer> {
    List<Group> findGroupByTutorId(Integer id);
}
