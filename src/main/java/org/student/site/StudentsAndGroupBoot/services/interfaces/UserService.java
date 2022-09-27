package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.student.site.StudentsAndGroupBoot.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
}
