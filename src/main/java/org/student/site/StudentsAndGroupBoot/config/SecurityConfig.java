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
    @Autowired
    private UserServiceImpl userService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/groups", "/students", "/tutors")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/groups/**", "/students/**", "/tutors/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .antMatchers(HttpMethod.POST, "/groups/**", "/students/**", "/tutors/**").hasRole(Role.ADMIN.name())
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
                    .roles(userList.get(i).getRole() + userList.get(i).getId())
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
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
