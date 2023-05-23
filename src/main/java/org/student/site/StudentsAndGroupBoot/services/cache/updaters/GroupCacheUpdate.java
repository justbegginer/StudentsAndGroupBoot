package org.student.site.StudentsAndGroupBoot.services.cache.updaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;
import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.repo.GroupRepo;
import org.student.site.StudentsAndGroupBoot.services.impl.GroupServiceImpl;

import java.util.List;

@Component
public class GroupCacheUpdate {
    private final GroupRepo groupRepo;

    public GroupCacheUpdate(@Autowired GroupRepo groupRepo){
        this.groupRepo = groupRepo;
    }
    @CachePut(cacheNames = "allGroups")
    public List<Group> update() {
        return groupRepo.findAll();
    }
}
