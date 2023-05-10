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
    private String login;

    @Email(message = "this is not a valid email address")
    @NotEmpty(message = "user's email shouldn't be empty")
    @Size(min = 2, max = 30, message = "user's email should be between 2 and 30")
    private String email;

    @NotEmpty(message = "user's password shouldn't be empty")
    @Size(min = 8, max = 30, message = "user's password should be between 8 and 30")
    @Pattern(regexp = "^([a-zA-Z]*[0-9]+)+([a-zA-Z]*)$",message = "user's password should contains only letters, and at least one digit")
    private String password;

    private int userId;


    public void setLoginBasedOnEmail(){
        if (this.email == null){
            return;
        }
        this.login = this.email.split("@")[0];
    }
}
