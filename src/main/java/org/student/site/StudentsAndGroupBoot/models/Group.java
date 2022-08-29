package org.student.site.StudentsAndGroupBoot.models;

import lombok.*;
import org.student.site.StudentsAndGroupBoot.validation.annotation.TutorExists;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    @TutorExists()
    @Column(name = "tutorid")
    private int tutorId;
}
