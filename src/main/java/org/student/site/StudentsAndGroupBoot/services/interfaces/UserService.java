package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.student.site.StudentsAndGroupBoot.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Integer id);

    List<User> findAllByRole(String role);

    void save(User user);

    void delete(User user);

    User findTopByRoleAndUserId(String role, Integer userId);
}
