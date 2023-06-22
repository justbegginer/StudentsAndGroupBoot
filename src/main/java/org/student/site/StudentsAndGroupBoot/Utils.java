package org.student.site.StudentsAndGroupBoot;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.student.site.StudentsAndGroupBoot.models.Status;
import org.student.site.StudentsAndGroupBoot.models.StatusPattern;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class Utils {

    public static<T> String getErrorStatusFromBindingResult(Set<ConstraintViolation<T>> violationSet ) {
        StringBuilder errorMessage = new StringBuilder("Errors: ");
        for (ConstraintViolation  violation: violationSet) {
            errorMessage
                    .append(violation.getMessage())
                    .append(",");
        }
        errorMessage.deleteCharAt(errorMessage.length() -1);
        return errorMessage.toString();
    }
}
