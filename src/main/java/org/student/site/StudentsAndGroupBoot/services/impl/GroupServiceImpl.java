package org.student.site.StudentsAndGroupBoot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.services.cache.updaters.GroupCacheUpdate;
import org.student.site.StudentsAndGroupBoot.repo.GroupRepo;
import org.student.site.StudentsAndGroupBoot.services.interfaces.GroupService;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"allGroups"})
public class GroupServiceImpl implements GroupService {

    private final GroupRepo groupRepo;

    private final GroupCacheUpdate groupCacheUpdate;

    public GroupServiceImpl(@Autowired GroupRepo groupRepo, @Autowired GroupCacheUpdate group, GroupCacheUpdate groupCacheUpdate) {
        this.groupRepo = groupRepo;
        this.groupCacheUpdate = groupCacheUpdate;
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
    public void save(Group group) {
        groupRepo.save(group);
        groupCacheUpdate.update();
    }

    @Override
    public void delete(Group group) {
        groupRepo.delete(group);
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
