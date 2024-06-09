package com.bba.ebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bba.ebankingbackend.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte, String> {
}
