package org.student.site.StudentsAndGroupBoot.validation.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.student.site.StudentsAndGroupBoot.repo.GroupRepo;
import org.student.site.StudentsAndGroupBoot.validation.annotation.GroupExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GroupExistsValidator implements ConstraintValidator<GroupExists, Integer> {
    @Autowired
    private GroupRepo groupRepo;
    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        return groupRepo.findById(id).isPresent();
    }
}
