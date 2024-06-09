package com.bba.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.bba.ebankingbackend.dtos.ClientDTO;
import com.bba.ebankingbackend.entities.Customer;
import com.bba.ebankingbackend.exceptions.CustomerNotFoundException;
import com.bba.ebankingbackend.services.CompteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class ClientRestController {
    private CompteService compteService;
    @GetMapping("/customers")
    public List<ClientDTO> customers(){
        return compteService.listCustomers();
    }
    @GetMapping("/customers/search")
    public List<ClientDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return compteService.searchCustomers("%"+keyword+"%");
    }
    @GetMapping("/customers/{id}")
    public ClientDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return compteService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    public ClientDTO saveCustomer(@RequestBody ClientDTO ClientDTO){
        return compteService.saveCustomer(ClientDTO);
    }
    @PutMapping("/customers/{customerId}")
    public ClientDTO updateCustomer(@PathVariable Long customerId, @RequestBody ClientDTO ClientDTO){
        ClientDTO.setId(customerId);
        return compteService.updateCustomer(ClientDTO);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        compteService.deleteCustomer(id);
    }
}
