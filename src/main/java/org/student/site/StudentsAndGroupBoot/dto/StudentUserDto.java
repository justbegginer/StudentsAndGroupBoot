package org.student.site.StudentsAndGroupBoot.dto;

import lombok.Data;
import org.student.site.StudentsAndGroupBoot.models.Student;
import org.student.site.StudentsAndGroupBoot.models.User;

@Data
public class StudentUserDto {
    private Student student;

    private User user;
}
