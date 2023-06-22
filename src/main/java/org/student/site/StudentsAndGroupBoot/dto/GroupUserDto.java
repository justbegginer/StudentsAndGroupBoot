package org.student.site.StudentsAndGroupBoot.dto;

import lombok.Data;
import org.student.site.StudentsAndGroupBoot.models.Group;
import org.student.site.StudentsAndGroupBoot.models.User;

@Data
public class GroupUserDto {
    private Group group;

    private User user;
}
