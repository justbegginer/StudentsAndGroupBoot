package org.student.site.StudentsAndGroupBoot;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.student.site.StudentsAndGroupBoot.models.Status;
import org.student.site.StudentsAndGroupBoot.models.StatusPattern;

public class Utils {

    public static Status getErrorStatusFromBindingResult(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder("Errors: ");
        for (ObjectError suppressedField : bindingResult.getAllErrors()) {
            errorMessage
                    .append(suppressedField.getDefaultMessage())
                    .append(",");
        }
        errorMessage.deleteCharAt(errorMessage.length() -1);
        return new Status(false, StatusPattern.INVALID,errorMessage.toString());

    }
}
