package org.student.site.StudentsAndGroupBoot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"org.student.site.StudentsAndGroupBoot.*"})
@EnableCaching
public class StudentsAndGroupBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentsAndGroupBootApplication.class, args);
	}



}
