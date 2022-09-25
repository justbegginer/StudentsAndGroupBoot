package org.student.site.StudentsAndGroupBoot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.student.site.StudentsAndGroupBoot.*"})
public class StudentsAndGroupBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentsAndGroupBootApplication.class, args);
	}



}
