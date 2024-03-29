package org.student.site.StudentsAndGroupBoot.services.cache.updaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;
import org.student.site.StudentsAndGroupBoot.models.Tutor;
import org.student.site.StudentsAndGroupBoot.repo.TutorRepo;

import java.util.List;

@Component
public class TutorCacheUpdate implements CacheUpdater<Tutor>{

    private final TutorRepo tutorRepo;
    public TutorCacheUpdate(@Autowired TutorRepo tutorRepo){
        this.tutorRepo = tutorRepo;
    }
    @CacheEvict(cacheNames = "allTutors")
    @Override
    public List<Tutor> update(){
        return tutorRepo.findAll();
    }
}
