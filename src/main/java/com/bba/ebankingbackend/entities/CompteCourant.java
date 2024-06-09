package com.bba.ebankingbackend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@DiscriminatorValue(value = "CC")
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class CompteCourant extends Compte{
	private double decouvert;
}
