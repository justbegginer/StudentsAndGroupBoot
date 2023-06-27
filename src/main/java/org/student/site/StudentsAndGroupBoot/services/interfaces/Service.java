package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.student.site.StudentsAndGroupBoot.models.User;

import java.util.List;
import java.util.Optional;

public interface Service<T> {

    boolean isExist(Integer id);

    Optional<T> findById(Integer id);

    List<T> findAll();

    void delete(Integer id);

    void update(T value);
}
