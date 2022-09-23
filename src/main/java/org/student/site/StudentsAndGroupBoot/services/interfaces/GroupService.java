package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.models.Student;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    Optional<Group> findById(Integer id);

    List<Group> findAll();

    void save(Group group);

    void delete(Group group);

    List<Group> findGroupByTutorId(Integer id);
}
