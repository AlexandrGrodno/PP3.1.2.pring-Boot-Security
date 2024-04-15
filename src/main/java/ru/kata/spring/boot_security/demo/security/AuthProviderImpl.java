package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;


@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Autowired
    public AuthProviderImpl(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        String password = authentication.getCredentials().toString();
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Password incorrect");
        } else
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
