package org.student.site.StudentsAndGroupBoot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.student.site.StudentsAndGroupBoot.models.Tutor;
import org.student.site.StudentsAndGroupBoot.repo.TutorRepo;
import org.student.site.StudentsAndGroupBoot.services.interfaces.TutorService;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"allTutors"})
public class TutorServiceImpl implements TutorService {

    private final TutorRepo tutorRepo;

    public TutorServiceImpl(@Autowired TutorRepo tutorRepo) {
        this.tutorRepo = tutorRepo;
    }

    @Override
    public Optional<Tutor> findById(Integer id) {
        return tutorRepo.findById(id);
    }

    @Override
    @Cacheable
    public List<Tutor> findAll() {
        return tutorRepo.findAll();
    }

    @CachePut
    @Override
    public List<Tutor> update() {
        return tutorRepo.findAll();
    }

    @Override
    public void save(Tutor tutor) {
        tutorRepo.save(tutor);
        update();
    }

    @Override
    public void delete(Tutor tutor) {
        tutorRepo.delete(tutor);
        update();
    }

    @Override
    public List<Tutor> findTutorByName(String searchWord) {
        return tutorRepo.findTutorByName(searchWord);
    }

    @Override
    public List<Tutor> findTutorBySurname(String searchWorld) {
        return tutorRepo.findTutorBySurname(searchWorld);
    }

    @Override
    public List<Tutor> findTutorByQualification(String searchWorld) {
        return tutorRepo.findTutorByQualification(searchWorld);
    }

    @Override
    public List<Tutor> findTutorByIncludingInName(String searchWorld) {
        return tutorRepo.findTutorByIncludingInName(searchWorld);
    }

    @Override
    public List<Tutor> findTutorByIncludingInSurname(String searchWorld) {
        return tutorRepo.findTutorByIncludingInSurname(searchWorld);
    }

    @Override
    public List<Tutor> findTutorByIncludingInQualification(String searchWorld) {
        return tutorRepo.findTutorByIncludingInQualification(searchWorld);
    }

    @Override
    public List<Tutor> findTutorByIncludingInNameSurnameAndQualification(String searchWorld1, String searchWorld2, String searchWorld3) {
        return tutorRepo.findTutorByIncludingInNameSurnameAndQualification(searchWorld1, searchWorld2, searchWorld3);
    }

    @Override
    public Tutor findTopByOrderByIdDesc() {
        return tutorRepo.findTopByOrderByIdDesc();
    }
}
