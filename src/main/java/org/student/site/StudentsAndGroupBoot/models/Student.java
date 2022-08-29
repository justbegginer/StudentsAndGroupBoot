package org.student.site.StudentsAndGroupBoot.models;

import lombok.*;
import org.student.site.StudentsAndGroupBoot.validation.annotation.GroupExists;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "student name should not be empty")
    @Size(min = 2, max = 30, message = "student name should be between 2 and 30")
    @Pattern(regexp = "^[a-z]*$", message = "student name should contains only letters")
    private String name;
    @NotEmpty(message = "student surname should not be empty")
    @Size(min = 2, max = 30, message = "student surname should be between 2 and 30")
    @Pattern(regexp = "^[a-z]*$", message = "student surname should contains only letters")
    private String surname;
    @NotNull(message = "student groupNumber should not be empty")
    @Min(value = 1, message = "student groupNumber should be greater than zero")
    @GroupExists()
    @Column(name = "groupnumber")
    private int groupNumber;
}
