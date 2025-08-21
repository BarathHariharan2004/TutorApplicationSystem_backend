package com.tutorapp.controller;

import com.tutorapp.config.JwtTokenUtil;
import com.tutorapp.dto.ApiResponse;
import com.tutorapp.dto.LoginRequest;
import com.tutorapp.dto.LoginResponse;
import com.tutorapp.dto.TutorRegistrationDto;
import com.tutorapp.entity.User;
import com.tutorapp.service.CustomUserDetailsService;
import com.tutorapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid credentials!"));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String jwt = jwtTokenUtil.generateToken(userDetails);

        User user = userService.findByEmail(loginRequest.getEmail());

        return ResponseEntity.ok(new LoginResponse(jwt, user.getId(), user.getEmail(), user.getFullName(), user.getRole()));
    }

    @PostMapping("/register/tutor")
    public ResponseEntity<?> registerTutor(@Valid @RequestBody TutorRegistrationDto registrationDto) {
        try {
            User user = userService.registerTutor(registrationDto);
            return ResponseEntity.ok(new ApiResponse(true, "Tutor registered successfully!", user.getEmail()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}