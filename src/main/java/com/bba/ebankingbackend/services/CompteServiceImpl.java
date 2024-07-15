package com.bba.ebankingbackend.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bba.ebankingbackend.dtos.AccountHistoryDTO;
import com.bba.ebankingbackend.dtos.ClientDTO;
import com.bba.ebankingbackend.dtos.CompteCourantDTO;
import com.bba.ebankingbackend.dtos.CompteDTO;
import com.bba.ebankingbackend.dtos.CompteEpargneDTO;
import com.bba.ebankingbackend.dtos.OperationDTO;
import com.bba.ebankingbackend.entities.Client;
import com.bba.ebankingbackend.entities.Compte;
import com.bba.ebankingbackend.entities.CompteCourant;
import com.bba.ebankingbackend.entities.CompteEpargne;
import com.bba.ebankingbackend.entities.Operation;
import com.bba.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.bba.ebankingbackend.exceptions.CustomerNotFoundException;
import com.bba.ebankingbackend.mappers.BankAccountMapperImpl;
import com.bba.ebankingbackend.repositories.AccountOperationRepository;
import com.bba.ebankingbackend.repositories.ClientRepository;
import com.bba.ebankingbackend.repositories.CompteRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @AllArgsConstructor @Transactional @Slf4j
public class CompteServiceImpl implements CompteService{
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private BankAccountMapperImpl bankAccountMapperImpl;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AccountOperationRepository accountOperationRepository;

	@Override
    public CompteCourantDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Client customer=clientRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        CompteCourant currentAccount=new CompteCourant();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setDateCreation(new Date());
        currentAccount.setSolde(initialBalance);
        currentAccount.setDecouvert(overDraft);
        currentAccount.setClient(customer);
        CompteCourant savedBankAccount = compteRepository.save(currentAccount);
        return bankAccountMapperImpl.fromCompteCourant(savedBankAccount);
    }

	@Override
	public CompteEpargneDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
			throws CustomerNotFoundException {
		Client client = clientRepository.findById(customerId)
				.orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
		CompteEpargne compteEpargne = new CompteEpargne();
		compteEpargne.setClient(client);
		compteEpargne.setSolde(initialBalance);
		compteEpargne.setDateCreation(new Date());
		compteEpargne.setTauxInteret(interestRate);
		CompteEpargneDTO compteEpargneDTO = bankAccountMapperImpl.fromCompteEpargne(compteEpargne);
		return compteEpargneDTO;
	}

	@Override
	public CompteDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
		Compte compte = compteRepository.findById(accountId)
				.orElseThrow(()-> new BankAccountNotFoundException("Le compte d'id égale à : " + accountId + " n'existe pas !"));
		CompteDTO compteDTO = new CompteDTO();
		if(compte instanceof CompteCourant) {
			compteDTO = bankAccountMapperImpl.fromCompteCourant((CompteCourant) compte);
		}else {
			compteDTO = bankAccountMapperImpl.fromCompteEpargne((CompteEpargne) compte);
		}
		
		return compteDTO;
	}

	@Override
	public List<CompteDTO> bankAccountList() {
		List<Compte> comptes = compteRepository.findAll();
		List<CompteDTO> compteDTOs = comptes.stream().map(compte ->{
			if(compte instanceof CompteEpargne) {
				CompteEpargne compteEpargne = (CompteEpargne) compte;
				return bankAccountMapperImpl.fromCompteEpargne(compteEpargne);
			}else {
				CompteCourant compteCourant = (CompteCourant) compte;
				return bankAccountMapperImpl.fromCompteCourant(compteCourant);
			}
		}).collect(Collectors.toList());
		return compteDTOs;
	}

	@Override
	public AccountHistoryDTO getAccountHistory(String accountId, int page, int size)
			throws BankAccountNotFoundException {
		Compte bankAccount= compteRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Account not Found"));;
        Page<Operation> accountOperations = accountOperationRepository.findByCompteIdOrderByDateOperationDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<OperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> bankAccountMapperImpl.fromOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getSolde());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
	}
	@Override
    public List<CompteDTO> searchAccounts(String keyword) {
        List<Compte> comptes = compteRepository.findByIdentifiantBancaireContainsOrClientNomContainsIgnoreCase(keyword, keyword);
        return comptes.stream().map(bankAccountMapperImpl::toCompteDTO).collect(Collectors.toList());
    }
	public CompteDTO addCompte(CompteDTO compteDTO) {
        Compte compte = mapAndSaveCompte(compteDTO);
        return bankAccountMapperImpl.toCompteDTO(compte);
    }

    private Compte mapAndSaveCompte(CompteDTO compteDTO) {
        Compte compte;
        if (compteDTO instanceof CompteEpargneDTO) {
            compte = bankAccountMapperImpl.fromCompteEpargneDTO((CompteEpargneDTO) compteDTO);
        } else if (compteDTO instanceof CompteCourantDTO) {
            compte = bankAccountMapperImpl.fromCompteCourantDTO((CompteCourantDTO) compteDTO);
        } else {
            throw new IllegalArgumentException("Invalid DTO type");
        }

        return compteRepository.save(compte);
    }
}
