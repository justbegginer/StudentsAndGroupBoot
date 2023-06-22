package org.student.site.StudentsAndGroupBoot.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String role;

    @Getter
    @Setter
    private String login;

    @Pattern(regexp = "^[a-z][a-z0-9]*@[a-z]*\\.[a-z]{2,3}$",message = "this is not a valid email address")
    @NotEmpty(message = "user's email shouldn't be empty")
    @Getter
    private String email;

    @NotEmpty(message = "user's password shouldn't be empty")
    @Size(min = 8, max = 30, message = "user's password should be between 8 and 30")
    @Pattern(regexp = "^([a-zA-Z]*[0-9]+)+([a-zA-Z]*)$", message = "user's password should contains only letters, and at least one digit")
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private int userId;

    public void setEmail(String email) {
        this.email = email;
        this.login = this.email.split("@")[0];
    }
}
