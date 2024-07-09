package com.bba.ebankingbackend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bba.ebankingbackend.entities.AppUser;
import com.bba.ebankingbackend.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public AppUser save(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }

    @Override
    public AppUser update(AppUser user) {
        // Récupérer l'utilisateur existant
        AppUser existingUser = appUserRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Mettre à jour les champs nécessaires
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        // Enregistrer et retourner l'utilisateur mis à jour
        return appUserRepository.save(existingUser);
    }

    @Override
    public void delete(Long userId) {
        appUserRepository.deleteById(userId);
    }

    @Override
    public boolean existsByUsername(String username) {
        return appUserRepository.existsByUsername(username);
    }
}

