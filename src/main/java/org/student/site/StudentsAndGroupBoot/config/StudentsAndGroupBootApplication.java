package org.student.site.StudentsAndGroupBoot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = {"org.student.site.StudentsAndGroupBoot.*"})
public class StudentsAndGroupBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentsAndGroupBootApplication.class, args);
	}



}
