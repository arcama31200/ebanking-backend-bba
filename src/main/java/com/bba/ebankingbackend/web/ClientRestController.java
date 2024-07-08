package com.bba.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.bba.ebankingbackend.dtos.ClientDTO;
import com.bba.ebankingbackend.exceptions.CustomerNotFoundException;
import com.bba.ebankingbackend.services.ClientService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class ClientRestController {
    private ClientService clientService;
    
    @GetMapping("/customers")
    public List<ClientDTO> customers(){
        return clientService.listCustomers();
    }
    @GetMapping("/customers/search")
    public List<ClientDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return clientService.searchCustomers("%"+keyword+"%");
    }
    @GetMapping("/customers/{id}")
    public ClientDTO getCustomerById(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return clientService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    public ClientDTO saveCustomer(@RequestBody ClientDTO ClientDTO){
        return clientService.saveCustomer(ClientDTO);
    }
    @PutMapping("/customers/{customerId}")
    public ClientDTO updateCustomer(@PathVariable Long customerId, @RequestBody ClientDTO clientDTO){
    	clientDTO.setId(customerId);
        return clientService.updateCustomer(clientDTO);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
    	clientService.deleteClient(id);
    }
}
