package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.student.site.StudentsAndGroupBoot.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    List<User> findAllByRole(String role);

    void save(User user);

    void delete(User user);

    User findTopByRoleAndUserId(String role, Integer userId);
}
