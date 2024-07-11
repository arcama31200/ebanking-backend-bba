package com.bba.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.bba.ebankingbackend.dtos.ClientDTO;
import com.bba.ebankingbackend.exceptions.CustomerNotFoundException;
import com.bba.ebankingbackend.services.ClientService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ClientRestController {
    private ClientService clientService;
    
    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<ClientDTO> customers(){
        return clientService.listCustomers();
    }
    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<ClientDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return clientService.searchCustomers("%"+keyword+"%");
    }
    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ClientDTO getCustomerById(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return clientService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ClientDTO saveCustomer(@RequestBody ClientDTO ClientDTO){
        return clientService.saveCustomer(ClientDTO);
    }
    @PutMapping("/customers/{customerId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ClientDTO updateCustomer(@PathVariable Long customerId, @RequestBody ClientDTO clientDTO){
    	clientDTO.setId(customerId);
        return clientService.updateCustomer(clientDTO);
    }
    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteCustomer(@PathVariable Long id){
    	clientService.deleteClient(id);
    }
}
