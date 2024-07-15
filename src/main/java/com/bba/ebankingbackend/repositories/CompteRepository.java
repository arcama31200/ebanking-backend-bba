package com.bba.ebankingbackend.repositories;

import com.bba.ebankingbackend.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompteRepository extends JpaRepository<Compte, String> {
    List<Compte> findByIdentifiantBancaireContainsOrClientNomContainsIgnoreCase(String identifiantBancaire, String nom);
}
