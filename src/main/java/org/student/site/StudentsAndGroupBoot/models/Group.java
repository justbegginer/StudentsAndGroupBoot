package org.student.site.StudentsAndGroupBoot.models;

import lombok.*;
import org.student.site.StudentsAndGroupBoot.validation.annotation.TutorExists;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "tutorId should not be empty")
    @Min(value = 1, message = "tutorId should not be more than 1")
    //@TutorExists()
    @Column(name = "tutorid")
    private Integer tutorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && tutorId == group.tutorId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tutorId);
    }
}
