package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.student.site.StudentsAndGroupBoot.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    List<User> findAllByRole(String role);

    Optional<User> findTopByRoleAndUserId(String role, Integer userId);
}
