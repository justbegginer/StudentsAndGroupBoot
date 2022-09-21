package org.student.site.StudentsAndGroupBoot.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "user's role shouldn't be empty")
    @Size(min = 2, max = 30, message = "user's role should be between 2 and 30")
    @Pattern(regexp = "^[a-z]*$",message = "user's role should contains only letters")
    private String role;

    @NotEmpty(message = "user's type shouldn't be empty")
    @Size(min = 2, max = 30, message = "user's type should be between 2 and 30")
    @Pattern(regexp = "^[a-z]*$",message = "user's type should contains only letters")
    private String type;

    @Email
    @NotEmpty(message = "user's email shouldn't be empty")
    @Size(min = 2, max = 30, message = "user's email should be between 2 and 30")
    @Pattern(regexp = "^[a-z]*$",message = "user's email should contains only letters")
    private String email;

    @NotEmpty(message = "user's password shouldn't be empty")
    @Size(min = 2, max = 30, message = "user's password should be between 2 and 30")
    @Pattern(regexp = "^[a-z]*$",message = "user's password should contains only letters")
    private String password;

    @Min(value = 1, message = "user's id should be more than 1")
    private int userId;
}
