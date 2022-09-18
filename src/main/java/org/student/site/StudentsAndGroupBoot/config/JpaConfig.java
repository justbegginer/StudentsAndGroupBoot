package org.student.site.StudentsAndGroupBoot.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("org.student.site.StudentsAndGroupBoot.*")
@EntityScan("org.student.site.StudentsAndGroupBoot.*")
public class JpaConfig {
}
