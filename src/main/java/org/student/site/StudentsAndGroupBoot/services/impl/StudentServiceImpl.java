package org.student.site.StudentsAndGroupBoot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.repo.StudentRepo;
import org.student.site.StudentsAndGroupBoot.services.cache.updaters.StudentCacheUpdate;
import org.student.site.StudentsAndGroupBoot.services.interfaces.StudentService;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"allStudents"})
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;

    private final StudentCacheUpdate studentCacheUpdate;

    public StudentServiceImpl(@Autowired StudentRepo studentRepo, @Autowired StudentCacheUpdate studentCacheUpdate) {
        this.studentRepo = studentRepo;
        this.studentCacheUpdate= studentCacheUpdate;
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
    public void save(Student student) {
        studentRepo.save(student);
        studentCacheUpdate.update();
    }

    @Override
    public void delete(Student student) {
        studentRepo.delete(student);
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
