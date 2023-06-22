package org.student.site.StudentsAndGroupBoot.dto;

import lombok.Data;
import org.student.site.StudentsAndGroupBoot.models.Tutor;
import org.student.site.StudentsAndGroupBoot.models.User;

@Data
public class TutorUserDto {
    private Tutor tutor;

    private User user;

}
