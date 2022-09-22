package org.student.site.StudentsAndGroupBoot.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return id == tutor.id && Objects.equals(name, tutor.name) && Objects.equals(surname, tutor.surname) && Objects.equals(qualification, tutor.qualification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, qualification);
    }
}
