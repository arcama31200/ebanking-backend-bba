package com.bba.ebankingbackend.web;

import org.sid.ebankingbackend.dtos.*;
import org.springframework.web.bind.annotation.*;

import com.bba.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.bba.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.bba.ebankingbackend.services.CompteService;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CompteRestController {
    private CompteService compteService;

    public CompteRestController(CompteService compteService) {
        this.compteService = compteService;
    }

    @GetMapping("/accounts/{accountId}")
    public CompteDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return compteService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<CompteDTO> listAccounts(){
        return compteService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<OperationDTO> getHistory(@PathVariable String accountId){
        return compteService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return compteService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.compteService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.compteService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.compteService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }
}
