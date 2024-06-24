package com.bba.ebankingbackend.entities;

import java.util.Date;
import java.util.List;

import com.bba.ebankingbackend.enums.Etat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @AllArgsConstructor @NoArgsConstructor @Data @ToString
public class Client {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String prenom;
	@Temporal(value = TemporalType.DATE)
	private Date dateNaissance;
	private int age;
	@Temporal(value = TemporalType.DATE)
	private Date dateInscription;
	@Enumerated(value = EnumType.STRING)
	private Etat etat;
	@OneToOne(mappedBy = "client")
	private Adresse adresse;
	@OneToMany(mappedBy="client")
	private List<Compte> Comptes;
	private String email;
}
