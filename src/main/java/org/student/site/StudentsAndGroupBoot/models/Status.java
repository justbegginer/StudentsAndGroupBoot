package org.student.site.StudentsAndGroupBoot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Status {
    private boolean success;
    private StatusPattern statusType;
    private String message;
}
