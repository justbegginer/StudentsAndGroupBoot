package org.student.site.StudentsAndGroupBoot.dto;

import lombok.Data;

@Data
public class PasswordsPair {
    private String oldPassword;

    private String newPassword;
}
