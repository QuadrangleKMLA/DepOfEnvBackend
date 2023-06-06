package com.quadrangle.depofenvkmla.Config;

import com.quadrangle.depofenvkmla.Documents.User.User;
import com.quadrangle.depofenvkmla.Documents.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsernamePasswordAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByName(name).isPresent() ? userRepository.findByName(name).get() : null;
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getRole()));
                return new UsernamePasswordAuthenticationToken(name, password, authorities);
            } else {
                throw new BadCredentialsException("Invalid Password");
            }
        } else {
            throw new BadCredentialsException("User doesn't exist");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
