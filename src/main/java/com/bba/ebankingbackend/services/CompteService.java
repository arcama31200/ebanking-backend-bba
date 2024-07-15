package com.bba.ebankingbackend.services;


import java.util.List;

import com.bba.ebankingbackend.dtos.AccountHistoryDTO;
import com.bba.ebankingbackend.dtos.CompteDTO;
import com.bba.ebankingbackend.dtos.CompteCourantDTO;
import com.bba.ebankingbackend.dtos.CompteEpargneDTO;
import com.bba.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.bba.ebankingbackend.exceptions.CustomerNotFoundException;

public interface CompteService {
    CompteCourantDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    CompteEpargneDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    CompteDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<CompteDTO> bankAccountList();   
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
	List<CompteDTO> searchAccounts(String keyword);
	CompteDTO addCompte(CompteDTO compteDTO);
}
