package com.bba.ebankingbackend.entities;

import java.util.Date;
import java.util.List;

import com.bba.ebankingbackend.enums.AccountStatus;

import jakarta.persistence.DiscriminatorColumn;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4)
@Entity @Data @AllArgsConstructor @NoArgsConstructor @ToString
public abstract class Compte {
	@Id
	private String id;
	@Temporal(value = TemporalType.DATE)
	private Date dateCreation;
	private double solde;
	private String description;
	@ManyToOne
	private Client client;
	@OneToMany(mappedBy = "compte")
	private List<Operation> operations;
	@Enumerated(value = EnumType.STRING)
	private AccountStatus status;
}
