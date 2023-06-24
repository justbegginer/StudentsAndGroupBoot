package org.student.site.StudentsAndGroupBoot.services.interfaces;

import org.student.site.StudentsAndGroupBoot.models.User;

import java.util.List;
import java.util.Optional;

public interface Service<T> {

    Optional<T> findById(Integer id);

    List<T> findAll();

    void save(T value, User user);

    void delete(Integer id);

    void update(T value);
}
