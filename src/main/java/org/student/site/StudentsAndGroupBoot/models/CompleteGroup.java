package org.student.site.StudentsAndGroupBoot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CompleteGroup {
    private Group group;
    private Tutor tutor;
    private List<Student> studentList;
}
