package com.bba.ebankingbackend.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bba.ebankingbackend.dtos.OperationDTO;
import com.bba.ebankingbackend.entities.Compte;
import com.bba.ebankingbackend.entities.Operation;
import com.bba.ebankingbackend.enums.OperationType;
import com.bba.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.bba.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.bba.ebankingbackend.mappers.BankAccountMapperImpl;
import com.bba.ebankingbackend.repositories.AccountOperationRepository;
import com.bba.ebankingbackend.repositories.CompteRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class OperationServiceImpl implements OperationService{
	private CompteRepository compteRepository;
	private BankAccountMapperImpl bankAccountMapperImpl;
	private AccountOperationRepository accountOperationRepository;
	
	@Override
	public void debit(String accountId, double amount, String description)
			throws BankAccountNotFoundException, BalanceNotSufficientException {
		 Compte Compte=compteRepository.findById(accountId)
	                .orElseThrow(()->new BankAccountNotFoundException("Compte not found"));
	        if(Compte.getSolde()<amount)
	            throw new BalanceNotSufficientException("Balance not sufficient");
	        Operation accountOperation=new Operation();
	        accountOperation.setTypeOperation(OperationType.DEBIT);
	        accountOperation.setAmount(amount);
	        accountOperation.setDescription(description);
	        accountOperation.setDateOperation(new Date());
	        accountOperation.setCompte(Compte);
	        accountOperationRepository.save(accountOperation);
	        Compte.setSolde(Compte.getSolde()-amount);
	        compteRepository.save(Compte);
		
	}

	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		Compte Compte=compteRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Compte not found"));
        Operation accountOperation=new Operation();
        accountOperation.setTypeOperation(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setDateOperation(new Date());
        accountOperation.setCompte(Compte);
        accountOperationRepository.save(accountOperation);
        Compte.setSolde(Compte.getSolde()+amount);
        compteRepository.save(Compte);
		
	}

	@Override
	public void virement(String accountIdSource, String accountIdDestination, double amount)
			throws BankAccountNotFoundException, BalanceNotSufficientException {
		 debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
	     credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
		
	}
	@Override
    public List<OperationDTO> accountHistory(String accountId){
        List<Operation> accountOperations = accountOperationRepository.findByCompteId(accountId);
        return accountOperations.stream().map(op->bankAccountMapperImpl.fromOperation(op)).collect(Collectors.toList());
    }

}
