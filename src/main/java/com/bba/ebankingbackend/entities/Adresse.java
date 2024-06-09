package com.bba.ebankingbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Adresse {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ville;
	private String pays;
	private String numeroRue;
	private String libelleRue;
	private int codePostal;
	private String numeroPorte;
	private String etage;
	private String telephone;
	private String email;
	@OneToOne
	private Client client;
}
