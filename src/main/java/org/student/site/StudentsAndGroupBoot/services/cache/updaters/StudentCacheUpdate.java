package org.student.site.StudentsAndGroupBoot.services.cache.updaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.repo.StudentRepo;

import java.util.List;

@Component
public class StudentCacheUpdate {

    private final StudentRepo studentRepo;

    public StudentCacheUpdate(@Autowired StudentRepo studentRepo){
        this.studentRepo = studentRepo;
    }

    @CachePut(cacheNames = "allStudents")
    public List<Student> update(){
        return studentRepo.findAll();
    }
}
