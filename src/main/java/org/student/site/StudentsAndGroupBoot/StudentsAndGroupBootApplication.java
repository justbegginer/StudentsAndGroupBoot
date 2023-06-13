package org.student.site.StudentsAndGroupBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StudentsAndGroupBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentsAndGroupBootApplication.class, args);
    }


}
