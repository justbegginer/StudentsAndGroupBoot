package org.student.site.StudentsAndGroupBoot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.student.site.StudentsAndGroupBoot.models.Role;
import org.student.site.StudentsAndGroupBoot.services.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserServiceImpl userService;

    public SecurityConfig(@Autowired UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<org.student.site.StudentsAndGroupBoot.models.User> studentUsers = userService.findAllByRole("student");
        for (org.student.site.StudentsAndGroupBoot.models.User studentUser : studentUsers) {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/students/" + studentUser.getUserId() + "/**")
                    .hasRole(studentUser.getRole() + studentUser.getUserId());
        }
        List<org.student.site.StudentsAndGroupBoot.models.User> tutorUsers = userService.findAllByRole("tutor");
        for (org.student.site.StudentsAndGroupBoot.models.User tutorUser : tutorUsers) {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/tutors/" + tutorUser.getUserId() + "/**")
                    .hasRole(tutorUser.getRole() + tutorUser.getUserId());
        }
        List<org.student.site.StudentsAndGroupBoot.models.User> groupUsers = userService.findAllByRole("group");
        for (org.student.site.StudentsAndGroupBoot.models.User groupUser : groupUsers) {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/groups/" + groupUser.getUserId() + "/**")
                    .hasRole(groupUser.getRole() + groupUser.getUserId());
        }
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/groups", "/students", "/tutors")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/groups/**", "/students/**", "/tutors/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .antMatchers(HttpMethod.DELETE, "/groups/**", "/students/**", "/tutors/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PATCH, "/groups/**", "/students/**", "/tutors/**").hasRole(Role.ADMIN.name())
                .and()
                .formLogin();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        List<org.student.site.StudentsAndGroupBoot.models.User> userList = userService.findAll();
        List<UserDetails> springSecurityUserList = new ArrayList<>(userList.size());
        for (int i = 0; i < userList.size(); i++) {
            springSecurityUserList.add(User.builder()
                    .username(userList.get(i).getEmail())
                    .password(passwordEncoder().encode(userList.get(i).getPassword()))
                    .roles(userList.get(i).getRole() + userList.get(i).getUserId())
                    .build());
        }
        springSecurityUserList.add(User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(Role.ADMIN.name())
                .build());
        springSecurityUserList.add(User.builder()
                .username("username")
                .password(passwordEncoder().encode("username"))
                .roles(Role.USER.name())
                .build());
        return new InMemoryUserDetailsManager(
                springSecurityUserList
        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
