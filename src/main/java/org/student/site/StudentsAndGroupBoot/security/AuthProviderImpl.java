package org.student.site.StudentsAndGroupBoot.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;

public class AuthProviderImpl implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (Objects.equals(email, "admin") && Objects.equals(password, "root")){
            return new UsernamePasswordAuthenticationToken(null, null, null);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
