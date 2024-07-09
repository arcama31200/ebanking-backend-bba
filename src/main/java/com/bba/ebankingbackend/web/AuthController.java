package com.bba.ebankingbackend.web;

import com.bba.ebankingbackend.dtos.AuthenticationRequest;
import com.bba.ebankingbackend.dtos.AuthenticationResponse;
import com.bba.ebankingbackend.entities.AppUser;
import com.bba.ebankingbackend.services.UserService;
import com.bba.ebankingbackend.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AppUser newUser) {
        if (userService.findByUsername(newUser.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        // Assigner un rôle par défaut si aucun rôle n'est fourni
        if (newUser.getRoles() == null || newUser.getRoles().isEmpty()) {
            newUser.setRoles("USER");
        }

        // Enregistrer l'utilisateur
        AppUser savedUser = userService.save(newUser);

        return ResponseEntity.ok(savedUser);
    }
}
