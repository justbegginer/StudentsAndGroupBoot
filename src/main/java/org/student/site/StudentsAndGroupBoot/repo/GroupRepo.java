package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.student.site.StudentsAndGroupBoot.models.Group;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<Group, Integer> {
    List<Group> findGroupByTutorId(Integer id);
    Group findTopByOrderByIdDesc();
}
