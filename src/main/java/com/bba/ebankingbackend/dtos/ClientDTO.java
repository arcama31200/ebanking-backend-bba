package com.bba.ebankingbackend.dtos;

import lombok.Data;


@Data
public class ClientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
}
