package org.student.site.StudentsAndGroupBoot.validation.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.student.site.StudentsAndGroupBoot.repo.TutorRepo;
import org.student.site.StudentsAndGroupBoot.validation.annotation.TutorExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TutorExistsValidator implements ConstraintValidator<TutorExists, Integer> {
    @Autowired
    private TutorRepo tutorRepo;
    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        return tutorRepo.findById(id).isPresent();
    }
}
