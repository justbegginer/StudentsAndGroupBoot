package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.student.site.StudentsAndGroupBoot.exceptions.IncorrectDataException;
import org.student.site.StudentsAndGroupBoot.exceptions.NotFoundException;
import org.student.site.StudentsAndGroupBoot.models.Status;
import org.student.site.StudentsAndGroupBoot.models.StatusPattern;

@ControllerAdvice
public class ExceptionHandlers{
    @ExceptionHandler
    public ResponseEntity<Status> notFoundException(NotFoundException notFoundException){
        return new ResponseEntity<>(new Status(false, StatusPattern.NOT_FOUND, notFoundException.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Status> incorrectException(IncorrectDataException incorrectException){
        return new ResponseEntity<>(new Status(false, StatusPattern.INVALID, incorrectException.getMessage()),
                HttpStatus.NOT_ACCEPTABLE);
    }
}
