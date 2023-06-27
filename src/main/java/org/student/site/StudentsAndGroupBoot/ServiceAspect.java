package org.student.site.StudentsAndGroupBoot;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.student.site.StudentsAndGroupBoot.exceptions.IncorrectDataException;
import org.student.site.StudentsAndGroupBoot.models.Group;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Aspect
@Component
public class ServiceAspect {

    @Autowired
    private Validator validator;
    @Pointcut("execution(public void org.student.site.StudentsAndGroupBoot.services.impl.*Impl.save(*, *))")
    public void saveMethods(){
    }
    @Before("saveMethods() && args(value, secondValue)")
    public void checkCorrectionOfData(Object value, Object secondValue){
        Set<ConstraintViolation<Object>> violationSet = validator.validate(value);
        if (!violationSet.isEmpty()) {
            throw new IncorrectDataException(Utils.getErrorStatusFromBindingResult(violationSet));
        }
    }
}
