package org.student.site.StudentsAndGroupBoot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.services.cache.updaters.GroupCacheUpdate;
import org.student.site.StudentsAndGroupBoot.repo.GroupRepo;
import org.student.site.StudentsAndGroupBoot.services.interfaces.GroupService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"allGroups"})
public class GroupServiceImpl implements GroupService {

    private final GroupRepo groupRepo;

    private final TutorServiceImpl tutorService;

    private final UserServiceImpl userService;

    private final GroupCacheUpdate groupCacheUpdate;

    @Autowired
    public GroupServiceImpl(GroupRepo groupRepo,
                            TutorServiceImpl tutorService,
                            UserServiceImpl userService,
                            GroupCacheUpdate groupCacheUpdate) {
        this.groupRepo = groupRepo;
        this.tutorService = tutorService;
        this.userService = userService;
        this.groupCacheUpdate = groupCacheUpdate;
    }

    @Override
    public boolean isExist(Integer id) {
        return groupRepo.existsById(id);
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
        tutorService.findById(group.getTutorId()); // throws exception if this tutor doesn't exist
        groupRepo.save(group);
        user.setRole("group");
        user.setUserId(groupRepo.findTopByOrderByIdDesc().getId());
        userService.save(user);
        groupCacheUpdate.update();
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Group group = groupRepo.findById(id).get();
        groupRepo.delete(group);
        userService.delete(
                userService.findTopByRoleAndUserId("group", group.getId()).get().getId());
        groupCacheUpdate.update();
    }

    @Override
    public void update(Group group) {
        tutorService.findById(group.getId()); // throws exception if this tutor doesn't exist
        if (!isExist(group.getId())){
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
