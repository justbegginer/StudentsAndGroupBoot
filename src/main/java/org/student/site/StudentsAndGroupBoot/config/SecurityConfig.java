package org.student.site.StudentsAndGroupBoot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.student.site.StudentsAndGroupBoot.models.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .antMatchers("/groups")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/groups/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .antMatchers(HttpMethod.POST, "/groups/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/groups/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PATCH, "/groups/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/students/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .antMatchers(HttpMethod.POST, "/students/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/students/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PATCH, "/students/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/tutors/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .antMatchers(HttpMethod.POST, "/tutors/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/tutors/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PATCH, "/tutors/**").hasRole(Role.ADMIN.name())
                .and()
                .formLogin();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles(Role.ADMIN.name())
                        .build(),
                User.builder()
                        .username("username")
                        .password(passwordEncoder().encode("username"))
                        .roles(Role.USER.name())
                        .build()
        );
    }
    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
