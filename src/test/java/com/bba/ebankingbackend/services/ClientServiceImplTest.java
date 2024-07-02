package com.bba.ebankingbackend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.bba.ebankingbackend.dtos.ClientDTO;
import com.bba.ebankingbackend.entities.Client;
import com.bba.ebankingbackend.exceptions.CustomerNotFoundException;
import com.bba.ebankingbackend.mappers.BankAccountMapperImpl;
import com.bba.ebankingbackend.repositories.ClientRepository;

import jakarta.transaction.Transactional;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BankAccountMapperImpl bankAccountMapperImpl;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCustomer() {
        // Given
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setEmail("belkahla.bilal@gmail.com");
        clientDTO.setId(1L);
        clientDTO.setNom("Bilal");
        Client client = new Client();
        when(bankAccountMapperImpl.fromClientDTO(clientDTO)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);

        // When
        ClientDTO savedClientDTO = clientService.saveCustomer(clientDTO);

        // Then
        assertNotNull(savedClientDTO);
        // Ajoutez d'autres assertions spécifiques basées sur votre implémentation
    }

    @Test
    public void testListCustomers() {
        // Given
        List<Client> clients = new ArrayList<>();
        Client client = new Client();
        client.setNom("John");
        client.setPrenom("Doe");
        clients.add(client);
        when(clientRepository.findAll()).thenReturn(clients);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setNom("John");
        when(bankAccountMapperImpl.fromClient(client)).thenReturn(clientDTO);

        // When
        List<ClientDTO> clientDTOs = clientService.listCustomers();

        // Then
        assertNotNull(clientDTOs);
        assertEquals(1, clientDTOs.size());
        assertEquals("John", clientDTOs.get(0).getNom());
    }

    @Test
    public void testGetCustomer() throws CustomerNotFoundException {
        // Given
        Long customerId = 1L;
        Client client = new Client();
        when(clientRepository.findById(customerId)).thenReturn(Optional.of(client));
        when(bankAccountMapperImpl.fromClient(client)).thenReturn(new ClientDTO());

        // When
        ClientDTO clientDTO = clientService.getCustomer(customerId);

        // Then
        assertNotNull(clientDTO);
        // Ajoutez d'autres assertions spécifiques basées sur votre implémentation
    }

    @Test
    public void testUpdateCustomer() {
        // Given
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L); // Assurez-vous que l'ID est correctement défini si nécessaire
        clientDTO.setNom("John");

        Client client = new Client();
        client.setId(1L); // Assurez-vous que l'ID est correctement défini
        client.setNom("John");
        client.setPrenom("Doe");

        when(bankAccountMapperImpl.fromClientDTO(clientDTO)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(bankAccountMapperImpl.fromClient(client)).thenReturn(clientDTO); // Ajout du mapping retour

        // When
        ClientDTO updatedClientDTO = clientService.updateCustomer(clientDTO);

        // Then
        assertNotNull(updatedClientDTO);
        assertEquals("John", updatedClientDTO.getNom());
    }
    @Test
    public void testDeleteClient() {
        // Given
        Long clientId = 1L;

        // When
        clientService.deleteClient(clientId);

        // Then
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    public void testSearchCustomers() {
        // Given
        String keyword = "search";
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        when(clientRepository.searchCustomer(keyword)).thenReturn(clients);

        // When
        List<ClientDTO> clientDTOs = clientService.searchCustomers(keyword);

        // Then
        assertNotNull(clientDTOs);
        assertEquals(1, clientDTOs.size());
        // Ajoutez d'autres assertions spécifiques basées sur votre implémentation
    }
}
