package org.student.site.StudentsAndGroupBoot.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.student.site.StudentsAndGroupBoot.Utils;
import org.student.site.StudentsAndGroupBoot.exceptions.IncorrectDataException;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.services.impl.ServiceBuilder;
import org.student.site.StudentsAndGroupBoot.services.interfaces.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Aspect
@Component
public class ServicesAspect {

    @Autowired
    private Validator validator;

    @Autowired
    private ServiceBuilder serviceBuilder;

    // don't work for user service
    @Pointcut("execution(public void org.student.site.StudentsAndGroupBoot.services.impl.*Impl.save(*, *))")
    public void saveMethods(){
    }
    @Pointcut("execution(public void org.student.site.StudentsAndGroupBoot.services.impl.*Impl.update(*))")
    public void updateMethods(){
    }

    @Pointcut("execution(public * org.student.site.StudentsAndGroupBoot.services.impl.*Impl.findById(*))")
    public void findByIdMethods(){
    }

    @Pointcut("execution(public void org.student.site.StudentsAndGroupBoot.services.impl.*Impl.delete(*))")
    public void deleteMethods(){
    }

    @Before(value = "saveMethods() && args(value, secondValue)", argNames = "value,secondValue")
    public void checkCorrectionOfData(Object value, Object secondValue){
        Set<ConstraintViolation<Object>> violationSet = validator.validate(value);
        if (!violationSet.isEmpty()) {
            throw new IncorrectDataException(Utils.getErrorStatusFromBindingResult(violationSet));
        }
    }

    @Before("updateMethods() && args(value)")
    public void checkCorrectionOfData(Object value){
        Set<ConstraintViolation<Object>> violationSet = validator.validate(value);
        if (!violationSet.isEmpty()) {
            throw new IncorrectDataException(Utils.getErrorStatusFromBindingResult(violationSet));
        }
    }

    @Before("(findByIdMethods() || deleteMethods()) && args(id)")
    public void isFindEntity(JoinPoint joinPoint, Integer id){
        Service service = serviceBuilder.buildService(joinPoint.getThis().getClass().getName()
                .split("\\$")[0]);
        if(!service.isExist(id)){
            throw new NotFoundException(service.getClass().getName()
                    .split("impl\\.")[1].split("Service")[0]
                    +" with id = " + id+" doesn't exist");
        }
    }


}
