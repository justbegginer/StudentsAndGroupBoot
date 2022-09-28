package org.student.site.StudentsAndGroupBoot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.repo.UserRepo;
import org.student.site.StudentsAndGroupBoot.services.interfaces.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public List<User> findAllByRole(String role) {
        return userRepo.findAllByRole(role);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public void delete(User user) {
        userRepo.delete(user);
    }
}
