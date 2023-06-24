package org.student.site.StudentsAndGroupBoot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.student.site.StudentsAndGroupBoot.Utils;
import org.student.site.StudentsAndGroupBoot.exceptions.IncorrectDataException;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.repo.StudentRepo;
import org.student.site.StudentsAndGroupBoot.services.cache.updaters.StudentCacheUpdate;
import org.student.site.StudentsAndGroupBoot.services.interfaces.StudentService;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@CacheConfig(cacheNames = {"allStudents"})
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;

    private final UserServiceImpl userService;

    private final StudentCacheUpdate studentCacheUpdate;

    private final Validator validator;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo,
                              UserServiceImpl userService,
                              StudentCacheUpdate studentCacheUpdate,
                              Validator validator) {
        this.studentRepo = studentRepo;
        this.userService = userService;
        this.studentCacheUpdate= studentCacheUpdate;
        this.validator = validator;
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return studentRepo.findById(id);
    }

    @Override
    @Cacheable
    public List<Student> findAll() {
        return studentRepo.findAll();
    }

    @Override
    @Transactional
    public void save(Student student, User user) {
        Set<ConstraintViolation<Student>> violationSet = validator.validate(student);
        if (!violationSet.isEmpty()){
            throw new IncorrectDataException(Utils.getErrorStatusFromBindingResult(violationSet));
        }
        studentRepo.save(student);
        user.setRole("student");
        user.setUserId(studentRepo.findTopByOrderByIdDesc().getId());
        userService.save(user);
        studentCacheUpdate.update();
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        if (optionalStudent.isEmpty()) {
            throw new NotFoundException("Student with id = " + id + " doesn't exist");
        }
        studentRepo.delete(optionalStudent.get());
        userService.delete(
                userService.findTopByRoleAndUserId("student", optionalStudent.get().getId()).get());
        studentCacheUpdate.update();
    }

    @Override
    public void update(Student student) {
        if (studentRepo.findById(student.getId()).isEmpty()) {
            throw new NotFoundException("Student with id = " + student.getId() + " doesn't exist");
        }
        Set<ConstraintViolation<Student>> violationSet = validator.validate(student);
        if (!violationSet.isEmpty()){
            throw new IncorrectDataException(Utils.getErrorStatusFromBindingResult(violationSet));
        }
        System.out.println("student changed");
        studentRepo.save(student);
        studentCacheUpdate.update();
    }

    @Override
    public List<Student> findStudentByName(String searchWord) {
        return studentRepo.findStudentByName(searchWord);
    }

    @Override
    public List<Student> findStudentBySurname(String searchWorld) {
        return studentRepo.findStudentBySurname(searchWorld);
    }

    @Override
    public List<Student> findStudentByGroupNumber(Integer id) {
        return studentRepo.findStudentByGroupNumber(id);
    }

    @Override
    public List<Student> findStudentByIncludingInName(String searchWorld) {
        return studentRepo.findStudentByIncludingInName(searchWorld);
    }

    @Override
    public List<Student> findStudentByIncludingInSurname(String searchWorld) {
        return studentRepo.findStudentByIncludingInSurname(searchWorld);
    }

    @Override
    public List<Student> findStudentByIncludingInNameAndSurname(String searchWord1, String searchWord2) {
        return studentRepo.findStudentByPartlyIncludingInNameAndSurname(searchWord1, searchWord2);
    }

    @Override
    public List<Student> findStudentByPartlyIncludingInNameAndSurname(String searchWorld1, String searchWorld2) {
        return studentRepo.findStudentByPartlyIncludingInNameAndSurname(searchWorld1, searchWorld2);
    }

    @Override
    public List<Student> findStudentWhichNotInGroup(Integer id) {
        return studentRepo.findStudentWhichNotInGroup(id);
    }

    @Override
    public Student findTopByOrderByIdDesc() {
        return studentRepo.findTopByOrderByIdDesc();
    }
}
