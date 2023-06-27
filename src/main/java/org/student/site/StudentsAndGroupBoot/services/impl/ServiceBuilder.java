package org.student.site.StudentsAndGroupBoot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.Tutor;
import org.student.site.StudentsAndGroupBoot.models.User;
import org.student.site.StudentsAndGroupBoot.services.interfaces.Service;
import org.student.site.StudentsAndGroupBoot.services.interfaces.StudentService;

import javax.persistence.Access;
import java.util.Objects;

@Component
public class ServiceBuilder {
    private GroupServiceImpl groupService;

    private UserServiceImpl userService;

    private TutorServiceImpl tutorService;

    private StudentServiceImpl studentService;

    @Autowired
    public ServiceBuilder(GroupServiceImpl groupService,
                          UserServiceImpl userService,
                          TutorServiceImpl tutorService,
                          StudentServiceImpl studentService){
        this.groupService = groupService;
        this.userService = userService;
        this.tutorService = tutorService;
        this.studentService = studentService;
    }

    public Service buildService(Object object){
        if(object.getClass() == Group.class){
            return groupService;
        } else if (object.getClass() == User.class) {
            return userService;
        } else if (object.getClass() == Student.class) {
            return studentService;
        }
        else if (object.getClass() == Tutor.class){
            return tutorService;
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public Service buildService(String name){
        if(Objects.equals(name, GroupServiceImpl.class.getName())){
            return groupService;
        } else if (Objects.equals(name, UserServiceImpl.class.getName())) {
            return userService;
        } else if (Objects.equals(name, StudentServiceImpl.class.getName())) {
            return studentService;
        }
        else if (Objects.equals(name, TutorServiceImpl.class.getName())){
            return tutorService;
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}
