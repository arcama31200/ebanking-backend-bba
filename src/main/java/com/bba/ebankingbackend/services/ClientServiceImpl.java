package com.bba.ebankingbackend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bba.ebankingbackend.dtos.ClientDTO;
import com.bba.ebankingbackend.entities.Client;
import com.bba.ebankingbackend.exceptions.CustomerNotFoundException;
import com.bba.ebankingbackend.mappers.BankAccountMapperImpl;
import com.bba.ebankingbackend.repositories.ClientRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
	
	private ClientRepository clientRepository;
	
	private BankAccountMapperImpl bankAccountMapperImpl;
	
	@Override
	public ClientDTO saveCustomer(ClientDTO clientDTO) {
		log.info("Saving new customer");
		Client client = bankAccountMapperImpl.fromClientDTO(clientDTO);
		Client savedClient = clientRepository.save(client);
		log.info("saved new customer");
		return bankAccountMapperImpl.fromClient(savedClient);
	}

	@Override
	public List<ClientDTO> listCustomers() {
		List<Client> clients = clientRepository.findAll();
		List<ClientDTO> clientDTOs = clients.stream().map(c -> bankAccountMapperImpl.fromClient(c))
				.collect(Collectors.toList());
		return clientDTOs;
	}

	@Override
	public ClientDTO getCustomer(Long customerId) throws CustomerNotFoundException {
		Client client = clientRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(null));
		ClientDTO clientDTO = bankAccountMapperImpl.fromClient(client);
		return clientDTO;
	}

	@Override
	public ClientDTO updateCustomer(ClientDTO clientDTO) {
		Client client = bankAccountMapperImpl.fromClientDTO(clientDTO);
		Client savedClient = clientRepository.save(client);
		return bankAccountMapperImpl.fromClient(savedClient);
	}

	@Override
	public void deleteClient(Long clientId) {
		clientRepository.deleteById(clientId);	
	}

	@Override
	public List<ClientDTO> searchCustomers(String keyword) {
		List<Client> clients = clientRepository.searchCustomer(keyword);
		List<ClientDTO> clientDTOs = clients.stream().map(client -> bankAccountMapperImpl.fromClient(client))
				.collect(Collectors.toList());
		return clientDTOs;
	}

}
