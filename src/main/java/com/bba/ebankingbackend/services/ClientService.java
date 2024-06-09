package com.bba.ebankingbackend.services;

import java.util.List;

import com.bba.ebankingbackend.dtos.ClientDTO;
import com.bba.ebankingbackend.exceptions.CustomerNotFoundException;

public interface ClientService {
	ClientDTO saveCustomer(ClientDTO ClientDTO);
	List<ClientDTO> listCustomers();
	ClientDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    ClientDTO updateCustomer(ClientDTO ClientDTO);
    List<ClientDTO> searchCustomers(String keyword);
	void deleteClient(Long customerId);

}
