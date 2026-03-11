package com.igitan.springboothome.controller;

import com.igitan.springboothome.config.JwtUtils;
import com.igitan.springboothome.dto.AuthResponse;
import com.igitan.springboothome.dto.LoginRequest;
import com.igitan.springboothome.dto.RegisterRequest;
import com.igitan.springboothome.model.User;
import com.igitan.springboothome.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.igitan.springboothome.dto.Verify2FARequest;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        User user = userRepository.findByUsername(loginRequest.getUsername()).get();

        // Check if 2FA is needed
        if (user.isTwoFactorEnabled()) {
            String code = String.valueOf(100000 + new Random().nextInt(900000));
            user.setTwoFactorCode(code);
            userRepository.save(user);

            emailService.send2FACode(user.getEmail(), code);
            return ResponseEntity.ok("2FA code sent to your email. Please verify.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AuthResponse(jwt,
                user.getId(),
                userDetails.getUsername(),
                user.getEmail(),
                roles));
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<?> verify2FA(@Valid @RequestBody Verify2FARequest verifyRequest) {
        User user = userRepository.findByUsername(verifyRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getTwoFactorCode() == null || !user.getTwoFactorCode().equals(verifyRequest.getCode())) {
            return ResponseEntity.badRequest().body("Error: Invalid verification code!");
        }

        // Clear code after successful verification
        user.setTwoFactorCode(null);
        userRepository.save(user);

        // Authenticate manually to generate token
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null,
                new HashSet<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roles = user.getRoles().stream().collect(Collectors.toList());

        return ResponseEntity.ok(new AuthResponse(jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles));
    }

    @Autowired
    com.igitan.springboothome.service.EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);
        user.setTwoFactorEnabled(true);

        userRepository.save(user);

        // Send Welcome Email
        emailService.sendWelcomeEmail(user.getEmail(), user.getUsername());

        return ResponseEntity.ok("User registered successfully!");
    }
}
