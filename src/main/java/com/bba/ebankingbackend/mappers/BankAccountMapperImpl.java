package com.bba.ebankingbackend.mappers;

import com.bba.ebankingbackend.dtos.OperationDTO;
import com.bba.ebankingbackend.dtos.CompteCourantDTO;
import com.bba.ebankingbackend.dtos.CompteDTO;
import com.bba.ebankingbackend.dtos.ClientDTO;
import com.bba.ebankingbackend.dtos.CompteEpargneDTO;
import com.bba.ebankingbackend.entities.Operation;
import com.bba.ebankingbackend.entities.CompteCourant;
import com.bba.ebankingbackend.entities.Client;
import com.bba.ebankingbackend.entities.Compte;
import com.bba.ebankingbackend.entities.CompteEpargne;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public ClientDTO fromClient(Client client){
    	ClientDTO clientDTO = new ClientDTO();
    	BeanUtils.copyProperties(client, clientDTO);
    	return clientDTO;
    }
    public Client fromClientDTO(ClientDTO clientDTO){
    	Client client = new Client();
    	BeanUtils.copyProperties(clientDTO, client);
    	return client;
    }

    public CompteEpargneDTO fromCompteEpargne(CompteEpargne compteEpargne){
    	CompteEpargneDTO compteEpargneDTO = new CompteEpargneDTO();
    	compteEpargneDTO.setClientDTO(fromClient(compteEpargne.getClient()));
    	compteEpargneDTO.setType(compteEpargne.getClass().getSimpleName());
    	BeanUtils.copyProperties(compteEpargne, compteEpargneDTO);
    	return compteEpargneDTO;
    }

    public CompteEpargne fromCompteEpargneDTO(CompteEpargneDTO compteEpargneDTO){
        CompteEpargne compteEpargne = new CompteEpargne();
        compteEpargne.setClient(fromClientDTO(compteEpargneDTO.getClientDTO()));
        BeanUtils.copyProperties(compteEpargneDTO, compteEpargne);
        return compteEpargne;
    }

    public CompteCourantDTO fromCompteEpargne(CompteCourant compteCourant){
        CompteCourantDTO compteCourantDTO = new CompteCourantDTO();
        compteCourantDTO.setClientDTO(fromClient(compteCourant.getClient()));
        compteCourantDTO.setType(compteCourant.getClass().getSimpleName());
        BeanUtils.copyProperties(compteCourant, compteCourantDTO);
        return compteCourantDTO;
    }

    public CompteCourant fromCompteCourantDTO(CompteCourantDTO compteCourantDTO){
        CompteCourant compteCourant = new CompteCourant();
        compteCourant.setClient(fromClientDTO(compteCourantDTO.getClientDTO()));
        BeanUtils.copyProperties(compteCourantDTO, compteCourant);
        return compteCourant;
    }
    public CompteCourantDTO fromCompteCourant(CompteCourant compteCourant){
        CompteCourantDTO compteCourantDTO = new CompteCourantDTO();
        compteCourantDTO.setClientDTO(fromClient(compteCourant.getClient()));
        BeanUtils.copyProperties(compteCourant, compteCourantDTO);
        return compteCourantDTO;
    }

    public OperationDTO fromOperation(Operation operation){
        OperationDTO operationDTO = new OperationDTO();
        BeanUtils.copyProperties(operation, operationDTO);
        return operationDTO;
    }
    public Operation fromOperationDTO(OperationDTO OperationDTO){
        Operation operation = new Operation();
        BeanUtils.copyProperties(OperationDTO, operation);
        return operation;
    }
    public CompteDTO toCompteDTO(Compte compte) {
        CompteDTO compteDTO;
        if (compte instanceof CompteEpargne) {
            CompteEpargneDTO epargneDTO = new CompteEpargneDTO();
            // Mapper les champs appropriés vers le DTO
            epargneDTO.setId(compte.getId());
            epargneDTO.setBalance(((CompteEpargne) compte).getSolde());
            epargneDTO.setCreatedAt(((CompteEpargne) compte).getDateCreation());
            epargneDTO.setStatus(((CompteEpargne) compte).getStatus());
            // Vous devez mapper le client ici, mais pour simplifier cette réponse, nous l'ignorons.
            epargneDTO.setInterestRate(((CompteEpargne) compte).getTauxInteret());
            compteDTO = epargneDTO;
        } else if (compte instanceof CompteCourant) {
            CompteCourantDTO courantDTO = new CompteCourantDTO();
            // Mapper les champs appropriés vers le DTO
            courantDTO.setId(compte.getId());
            courantDTO.setBalance(((CompteCourant) compte).getSolde());
            courantDTO.setCreatedAt(((CompteCourant) compte).getDateCreation());
            courantDTO.setStatus(((CompteCourant) compte).getStatus());
            // Vous devez mapper le client ici, mais pour simplifier cette réponse, nous l'ignorons.
            courantDTO.setOverDraft(((CompteCourant) compte).getDecouvert());
            compteDTO = courantDTO;
        } else {
            throw new IllegalArgumentException("Invalid entity type");
        }

        return compteDTO;
    }

}
