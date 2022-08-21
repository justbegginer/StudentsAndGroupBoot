package org.student.site.StudentsAndGroupBoot.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tutor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "tutor name should not be empty")
    @Size(min = 2, max = 30, message = "tutor name should be between 2 and 30")
    @Pattern(regexp = "^[a-z]*$", message = "tutor name should contains only letters")
    private String name;
    @NotEmpty(message = "tutor surname should not be empty")
    @Size(min = 2, max = 30, message = "tutor surname should be between 2 and 30")
    @Pattern(regexp = "^[a-z]*$", message = "tutor surname should contains only letters")
    private String surname;
    @NotEmpty(message = "tutor qualification should not be empty")
    @Size(min = 2, max = 30, message = "tutor qualification should be between 2 and 30")
    @Pattern(regexp = "^[a-z]*$", message = "tutor qualification should contains only letters")
    private String qualification;
}
