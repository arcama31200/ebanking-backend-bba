package com.bba.ebankingbackend.mappers;

import com.bba.ebankingbackend.dtos.OperationDTO;
import com.bba.ebankingbackend.dtos.CompteCourantDTO;
import com.bba.ebankingbackend.dtos.ClientDTO;
import com.bba.ebankingbackend.dtos.CompteEpargneDTO;
import com.bba.ebankingbackend.entities.Operation;
import com.bba.ebankingbackend.entities.CompteCourant;
import com.bba.ebankingbackend.entities.Client;
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

}
