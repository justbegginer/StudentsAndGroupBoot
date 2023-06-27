package org.student.site.StudentsAndGroupBoot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.student.site.StudentsAndGroupBoot.Utils;
import org.student.site.StudentsAndGroupBoot.exceptions.IncorrectDataException;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.repo.UserRepo;
import org.student.site.StudentsAndGroupBoot.services.interfaces.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final Validator validator;

    @Autowired
    public UserServiceImpl(UserRepo userRepo,
                           Validator validator) {
        this.userRepo = userRepo;
        this.validator = validator;
    }

    @Override
    public boolean isExist(Integer id) {
        return userRepo.existsById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> findAllByRole(String role) {
        return userRepo.findAllByRole(role);
    }


    @Override
    public void save(User user){
        userRepo.save(user);
    }
    @Override
    public void update(User user) {
        if(isExist(user.getId())){
            throw new NotFoundException("User with id " + user.getId() + " doesn't exists");
        }
        userRepo.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepo.delete(userRepo.findById(id).get());
    }

    @Override
    public Optional<User> findTopByRoleAndUserId(String role, Integer userId) {
        return userRepo.findTopByRoleAndUserId(role, userId);
    }
}
