package org.student.site.StudentsAndGroupBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.student.site.StudentsAndGroupBoot.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
}
