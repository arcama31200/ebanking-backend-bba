package com.bba.ebankingbackend.services;

import java.util.List;

import com.bba.ebankingbackend.dtos.OperationDTO;
import com.bba.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.bba.ebankingbackend.exceptions.BankAccountNotFoundException;

public interface OperationService {
	void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void virement(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<OperationDTO> accountHistory(String accountId);
}
