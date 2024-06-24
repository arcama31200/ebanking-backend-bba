package com.bba.ebankingbackend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bba.ebankingbackend.dtos.OperationDTO;
import com.bba.ebankingbackend.entities.Compte;
import com.bba.ebankingbackend.entities.CompteCourant;
import com.bba.ebankingbackend.entities.CompteEpargne;
import com.bba.ebankingbackend.entities.Operation;
import com.bba.ebankingbackend.enums.OperationType;
import com.bba.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.bba.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.bba.ebankingbackend.mappers.BankAccountMapperImpl;
import com.bba.ebankingbackend.repositories.AccountOperationRepository;
import com.bba.ebankingbackend.repositories.CompteRepository;

@ExtendWith(MockitoExtension.class)
public class OperationServiceImplTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private BankAccountMapperImpl bankAccountMapperImpl;

    @Mock
    private AccountOperationRepository accountOperationRepository;

    @InjectMocks
    private OperationServiceImpl operationService;

    @BeforeEach
    public void setUp() {
        // Vous pouvez initialiser des mocks spécifiques ou effectuer des configurations ici
    }

    @Test
    public void testDebit() throws BankAccountNotFoundException, BalanceNotSufficientException {
        // Given
        String accountId = "accountId";
        double amount = 100.0;
        String description = "Debit operation";

        CompteCourant compte = new CompteCourant();
        compte.setId(accountId);
        compte.setSolde(200.0);
        when(compteRepository.findById(accountId)).thenReturn(Optional.of(compte));

        Operation savedOperation = new Operation();
        when(accountOperationRepository.save(any(Operation.class))).thenReturn(savedOperation);

        // When
        assertDoesNotThrow(() -> operationService.debit(accountId, amount, description));

        // Then
        assertEquals(compte.getSolde(), 100.0);
        verify(compteRepository, times(1)).save(compte);
    }

    @Test
    public void testDebitBalanceNotSufficient() {
        // Given
        String accountId = "accountId";
        double amount = 300.0;
        String description = "Debit operation";

        CompteCourant compte = new CompteCourant();
        compte.setId(accountId);
        compte.setSolde(200.0);
        when(compteRepository.findById(accountId)).thenReturn(Optional.of(compte));

        // When
        assertThrows(BalanceNotSufficientException.class, () -> operationService.debit(accountId, amount, description));

        // Then
        assertEquals(compte.getSolde(), 200.0);
        verify(compteRepository, never()).save(compte);
    }

    @Test
    public void testCredit() throws BankAccountNotFoundException {
        // Given
        String accountId = "accountId";
        double amount = 100.0;
        String description = "Credit operation";

        CompteEpargne compte = new CompteEpargne();
        compte.setId(accountId);
        compte.setSolde(200.0);
        when(compteRepository.findById(accountId)).thenReturn(Optional.of(compte));

        Operation savedOperation = new Operation();
        when(accountOperationRepository.save(any(Operation.class))).thenReturn(savedOperation);

        // When
        assertDoesNotThrow(() -> operationService.credit(accountId, amount, description));

        // Then
        assertEquals(compte.getSolde(), 300.0);
        verify(compteRepository, times(1)).save(compte);
    }

    @Test
    public void testVirement() throws BankAccountNotFoundException, BalanceNotSufficientException {
        // Given
        String accountIdSource = "accountIdSource";
        String accountIdDestination = "accountIdDestination";
        double amount = 100.0;

        CompteCourant compteSource = new CompteCourant();
        compteSource.setId(accountIdSource);
        compteSource.setSolde(300.0);
        when(compteRepository.findById(accountIdSource)).thenReturn(Optional.of(compteSource));

        CompteCourant compteDestination = new CompteCourant();
        compteDestination.setId(accountIdDestination);
        compteDestination.setSolde(200.0);
        when(compteRepository.findById(accountIdDestination)).thenReturn(Optional.of(compteDestination));

        Operation savedOperation = new Operation();
        when(accountOperationRepository.save(any(Operation.class))).thenReturn(savedOperation);

        // When
        assertDoesNotThrow(() -> operationService.virement(accountIdSource, accountIdDestination, amount));

        // Then
        assertEquals(compteSource.getSolde(), 200.0);
        assertEquals(compteDestination.getSolde(), 300.0);
        verify(compteRepository, times(2)).save(any(Compte.class));
    }

    @Test
    public void testAccountHistory() {
        // Given
        String accountId = "accountId";
        List<Operation> operations = new ArrayList<>();
        Operation operation1 = new Operation();
        operation1.setId(1L);
        operations.add(operation1);

        when(accountOperationRepository.findByCompteId(accountId)).thenReturn(operations);

        OperationDTO operationDTO = new OperationDTO();
        when(bankAccountMapperImpl.fromOperation(operation1)).thenReturn(operationDTO);

        // When
        List<OperationDTO> operationDTOs = operationService.accountHistory(accountId);

        // Then
        assertNotNull(operationDTOs);
        assertEquals(1, operationDTOs.size());
        // Ajoutez d'autres assertions spécifiques basées sur votre implémentation
    }
}
