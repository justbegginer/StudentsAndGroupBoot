package org.student.site.StudentsAndGroupBoot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.student.site.StudentsAndGroupBoot.Utils;
import org.student.site.StudentsAndGroupBoot.exceptions.IncorrectDataException;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.services.cache.updaters.GroupCacheUpdate;
import org.student.site.StudentsAndGroupBoot.repo.GroupRepo;
import org.student.site.StudentsAndGroupBoot.services.interfaces.GroupService;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@CacheConfig(cacheNames = {"allGroups"})
public class GroupServiceImpl implements GroupService {

    private final GroupRepo groupRepo;

    private UserServiceImpl userService;

    private final GroupCacheUpdate groupCacheUpdate;

    private final Validator validator;

    @Autowired
    public GroupServiceImpl(GroupRepo groupRepo,
                            UserServiceImpl userService,
                            GroupCacheUpdate groupCacheUpdate,
                            Validator validator) {
        this.groupRepo = groupRepo;
        this.userService = userService;
        this.groupCacheUpdate = groupCacheUpdate;
        this.validator = validator;
    }

    @Override
    public Optional<Group> findById(Integer id) {
        return groupRepo.findById(id);
    }

    @Cacheable
    @Override
    public List<Group> findAll() {
        return groupRepo.findAll();
    }

    @Override
    @Transactional
    public void save(Group group, User user) {
        groupRepo.save(group);
        user.setRole("group");
        user.setUserId(groupRepo.findTopByOrderByIdDesc().getId());
        userService.save(user);
        groupCacheUpdate.update();
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Optional<Group> optionalGroup = groupRepo.findById(id);
        if (optionalGroup.isEmpty()){
            throw new NotFoundException("There is no such group with id = " + id);
        }
        groupRepo.delete(optionalGroup.get());
        userService.delete(
                userService.findTopByRoleAndUserId("group", optionalGroup.get().getId()).get());
        groupCacheUpdate.update();
    }

    @Override
    public void update(Group group) {
        Set<ConstraintViolation<Group>> violationSet = validator.validate(group);
        if (!violationSet.isEmpty()) {
            throw new IncorrectDataException(Utils.getErrorStatusFromBindingResult(violationSet));
        }
        if (groupRepo.findById(group.getId()).isEmpty()){
            throw new NotFoundException("There is no such group with id = " + group.getId());
        }
        groupRepo.save(group);
        groupCacheUpdate.update();
    }

    @Override
    public List<Group> findGroupByTutorId(Integer id) {
        return groupRepo.findGroupByTutorId(id);
    }

    @Override
    public Group findTopByOrderByIdDesc() {
        return groupRepo.findTopByOrderByIdDesc();
    }

}
