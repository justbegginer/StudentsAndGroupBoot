package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.student.site.StudentsAndGroupBoot.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends Service<User>{

    @Override
    boolean isExist(Integer id);

    @Override
    List<User> findAll();

    @Override
    Optional<User> findById(Integer id);

    List<User> findAllByRole(String role);

    void save(User user);

    @Override
    void update(User user);

    @Override
    void delete(Integer id);

    Optional<User> findTopByRoleAndUserId(String role, Integer userId);
}
