package org.student.site.StudentsAndGroupBoot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.repo.GroupRepo;
import org.student.site.StudentsAndGroupBoot.services.interfaces.GroupService;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepo groupRepo;

    @Override
    public Optional<Group> findById(Integer id) {
        return groupRepo.findById(id);
    }

    @Override
    public List<Group> findAll() {
        return groupRepo.findAll();
    }

    @Override
    public void save(Group group) {
        groupRepo.save(group);
    }

    @Override
    public void delete(Group group) {
        groupRepo.delete(group);
    }

    @Override
    public List<Group> findGroupByTutorId(Integer id) {
        return groupRepo.findGroupByTutorId(id);
    }
}
