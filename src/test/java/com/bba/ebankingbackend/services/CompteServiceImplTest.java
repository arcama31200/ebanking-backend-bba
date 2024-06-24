package com.bba.ebankingbackend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.bba.ebankingbackend.dtos.AccountHistoryDTO;
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

@ExtendWith(MockitoExtension.class)
public class CompteServiceImplTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private BankAccountMapperImpl bankAccountMapperImpl;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AccountOperationRepository accountOperationRepository;

    @InjectMocks
    private CompteServiceImpl compteService;

    @BeforeEach
    public void setUp() {
        // Vous pouvez initialiser des mocks spécifiques ou effectuer des configurations ici
    }

    @Test
    public void testSaveCurrentBankAccount() throws CustomerNotFoundException {
        // Given
        double initialBalance = 1000.0;
        double overDraft = 500.0;
        Long customerId = 1L;

        Client client = new Client();
        client.setId(customerId);
        when(clientRepository.findById(customerId)).thenReturn(Optional.of(client));

        CompteCourant currentAccount = new CompteCourant();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setDateCreation(new Date());
        currentAccount.setSolde(initialBalance);
        currentAccount.setDecouvert(overDraft);
        currentAccount.setClient(client);
        when(compteRepository.save(any(CompteCourant.class))).thenReturn(currentAccount);

        CompteCourantDTO expectedDTO = new CompteCourantDTO();
        when(bankAccountMapperImpl.fromCompteCourant(currentAccount)).thenReturn(expectedDTO);

        // When
        CompteCourantDTO savedDTO = compteService.saveCurrentBankAccount(initialBalance, overDraft, customerId);

        // Then
        assertNotNull(savedDTO);
        assertEquals(expectedDTO, savedDTO);
        // Ajoutez d'autres assertions spécifiques basées sur votre implémentation
    }

    @Test
    public void testSaveSavingBankAccount() throws CustomerNotFoundException {
        // Given
        double initialBalance = 2000.0;
        double interestRate = 0.03;
        Long customerId = 2L;

        Client client = new Client();
        client.setId(customerId);
        when(clientRepository.findById(customerId)).thenReturn(Optional.of(client));

        CompteEpargne compteEpargne = new CompteEpargne();
        compteEpargne.setClient(client);
        compteEpargne.setSolde(initialBalance);
        compteEpargne.setDateCreation(new Date());
        compteEpargne.setTauxInteret(interestRate);
        when(bankAccountMapperImpl.fromCompteEpargne(compteEpargne)).thenReturn(new CompteEpargneDTO());

        // When
        CompteEpargneDTO savedDTO = compteService.saveSavingBankAccount(initialBalance, interestRate, customerId);

        // Then
        assertNotNull(savedDTO);
        // Ajoutez d'autres assertions spécifiques basées sur votre implémentation
    }

    @Test
    public void testGetBankAccount() throws BankAccountNotFoundException {
        // Given
        String accountId = UUID.randomUUID().toString();
        CompteCourant compteCourant = new CompteCourant();
        compteCourant.setId(accountId);
        when(compteRepository.findById(accountId)).thenReturn(Optional.of(compteCourant));
        when(bankAccountMapperImpl.fromCompteCourant(compteCourant)).thenReturn(new CompteCourantDTO());

        // When
        CompteDTO compteDTO = compteService.getBankAccount(accountId);

        // Then
        assertNotNull(compteDTO);
        // Ajoutez d'autres assertions spécifiques basées sur votre implémentation
    }

    @Test
    public void testBankAccountList() {
        // Given
        List<Compte> comptes = new ArrayList<>();
        comptes.add(new CompteCourant());
        when(compteRepository.findAll()).thenReturn(comptes);
        when(bankAccountMapperImpl.fromCompteCourant(any(CompteCourant.class))).thenReturn(new CompteCourantDTO());

        // When
        List<CompteDTO> compteDTOs = compteService.bankAccountList();

        // Then
        assertNotNull(compteDTOs);
        assertEquals(1, compteDTOs.size());
        // Ajoutez d'autres assertions spécifiques basées sur votre implémentation
    }

    @Test
    public void testGetAccountHistory() throws BankAccountNotFoundException {
        // Given
        String accountId = UUID.randomUUID().toString();
        int page = 0;
        int size = 10;
        CompteCourant compteCourant = new CompteCourant();
        compteCourant.setId(accountId);
        compteCourant.setSolde(1500.0);
        when(compteRepository.findById(accountId)).thenReturn(Optional.of(compteCourant));

        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation();
        operation.setDateOperation(new Date());
        operation.setAmount(500.0);
        operations.add(operation);

        when(accountOperationRepository.findByCompteIdOrderByDateOperationDesc(accountId, PageRequest.of(page, size)))
                .thenReturn(org.springframework.data.domain.Page.<Operation>empty());

        // When
        AccountHistoryDTO accountHistoryDTO = compteService.getAccountHistory(accountId, page, size);

        // Then
        assertNotNull(accountHistoryDTO);
        assertEquals(accountId, accountHistoryDTO.getAccountId());
        assertEquals(0, accountHistoryDTO.getAccountOperationDTOS().size());
        // Ajoutez d'autres assertions spécifiques basées sur votre implémentation
    }
}

