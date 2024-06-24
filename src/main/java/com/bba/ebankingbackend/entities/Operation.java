package com.bba.ebankingbackend.entities;

import java.util.Date;

import com.bba.ebankingbackend.enums.OperationType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Operation {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(value = TemporalType.DATE)
	private Date dateOperation;
	private double amount;
	@Enumerated(value = EnumType.STRING)
	private OperationType typeOperation;
	private String description;
	@ManyToOne
	private Compte compte;
}
