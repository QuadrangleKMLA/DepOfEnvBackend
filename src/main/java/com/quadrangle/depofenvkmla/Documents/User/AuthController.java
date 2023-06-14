package com.quadrangle.depofenvkmla.Documents.User;

import com.quadrangle.depofenvkmla.Payload.requestBody.LoginReq;
import com.quadrangle.depofenvkmla.Payload.requestBody.SignupReq;
import com.quadrangle.depofenvkmla.Payload.responseBody.JwtRes;
import com.quadrangle.depofenvkmla.Payload.responseBody.MessageRes;
import com.quadrangle.depofenvkmla.Security.JWT.JwtUtils;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody @NonNull LoginReq loginReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new JwtRes(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody @NonNull SignupReq signupReq) {
        if (userRepository.existsByUsername(signupReq.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageRes("Error: Username is already taken"));
        }

        if (userRepository.existsByEmail(signupReq.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageRes("Error: Email is already in use"));
        }

        User user = new User(signupReq.getUsername(), signupReq.getEmail(), passwordEncoder.encode(signupReq.getPassword()));
        Set<String> strRoles = signupReq.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageRes("Error: No role is specified"));
        } else {
            Role role = roleRepository.findByName(ERole.ROLE_MINISTER.getRole()).orElseThrow(() -> new RuntimeException("Error: Role not found."));
            roles.add(role);
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageRes("User registered successfully"));
    }
}
