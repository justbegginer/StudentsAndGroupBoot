package org.student.site.StudentsAndGroupBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@SpringBootApplication(scanBasePackages = {"org.student.site.StudentsAndGroupBoot"})
public class StudentsAndGroupBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentsAndGroupBootApplication.class, args);
	}



}
