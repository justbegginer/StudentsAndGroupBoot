package org.student.site.StudentsAndGroupBoot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.student.site.StudentsAndGroupBoot.Utils;
import org.student.site.StudentsAndGroupBoot.exceptions.IncorrectDataException;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.Tutor;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.repo.TutorRepo;
import org.student.site.StudentsAndGroupBoot.repo.UserRepo;
import org.student.site.StudentsAndGroupBoot.services.cache.updaters.TutorCacheUpdate;
import org.student.site.StudentsAndGroupBoot.services.interfaces.TutorService;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@CacheConfig(cacheNames = {"allTutors"})
public class TutorServiceImpl implements TutorService {

    private final TutorRepo tutorRepo;

    private final UserServiceImpl userService;

    private final TutorCacheUpdate tutorCacheUpdate;

    private final Validator validator;

    @Autowired
    public TutorServiceImpl(TutorRepo tutorRepo,
                            UserServiceImpl userService,
                            TutorCacheUpdate tutorCacheUpdate,
                            Validator validator) {
        this.tutorRepo = tutorRepo;
        this.userService = userService;
        this.tutorCacheUpdate = tutorCacheUpdate;
        this.validator = validator;
    }

    @Override
    public boolean isExist(Integer id) {
        return tutorRepo.existsById(id);
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

    @Override
    @Transactional
    public void save(Tutor tutor, User user) {
        tutorRepo.save(tutor);
        user.setRole("tutor");
        user.setUserId(tutor.getId());
        userService.save(user);
        tutorCacheUpdate.update();
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Tutor tutor = tutorRepo.findById(id).get();
        tutorRepo.delete(tutor);
        userService.delete(
                userService.findTopByRoleAndUserId("tutor", tutor.getId()).get().getId());
        tutorCacheUpdate.update();
    }

    @Override
    public void update(Tutor tutor) {
        if (!isExist(tutor.getId())) {
            throw new NotFoundException("Tutor with id = " + tutor.getId() + " doesn't exist'");
        }
        tutorRepo.save(tutor);
        tutorCacheUpdate.update();
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
