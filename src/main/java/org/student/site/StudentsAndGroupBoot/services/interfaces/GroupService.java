package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.models.User;

import java.util.List;
import java.util.Optional;

public interface GroupService extends Service<Group> {

    @Override
    boolean isExist(Integer id);

    @Override
    Optional<Group> findById(Integer id);

    @Override
    List<Group> findAll();

    void save(Group group, User user);

    @Override
    void delete(Integer id);


    @Override
    void update(Group group);

    List<Group> findGroupByTutorId(Integer id);

    Group findTopByOrderByIdDesc();
}
