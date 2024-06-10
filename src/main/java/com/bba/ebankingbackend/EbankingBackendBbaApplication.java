package com.bba.ebankingbackend;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bba.ebankingbackend.dtos.ClientDTO;
import com.bba.ebankingbackend.dtos.CompteCourantDTO;
import com.bba.ebankingbackend.dtos.CompteDTO;
import com.bba.ebankingbackend.dtos.CompteEpargneDTO;
import com.bba.ebankingbackend.entities.Client;
import com.bba.ebankingbackend.entities.CompteCourant;
import com.bba.ebankingbackend.entities.CompteEpargne;
import com.bba.ebankingbackend.entities.Operation;
import com.bba.ebankingbackend.enums.AccountStatus;
import com.bba.ebankingbackend.enums.OperationType;
import com.bba.ebankingbackend.exceptions.CustomerNotFoundException;
import com.bba.ebankingbackend.repositories.AccountOperationRepository;
import com.bba.ebankingbackend.repositories.ClientRepository;
import com.bba.ebankingbackend.repositories.CompteRepository;
import com.bba.ebankingbackend.services.ClientService;
import com.bba.ebankingbackend.services.CompteService;
import com.bba.ebankingbackend.services.OperationService;

@SpringBootApplication
public class EbankingBackendBbaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendBbaApplication.class, args);
	}
	@Bean
    CommandLineRunner commandLineRunner(CompteService bankAccountService, ClientService clientService, OperationService operationService){
        return args -> {
           Stream.of("Hassan","Imane","Mohamed").forEach(name->{
               ClientDTO customer=new ClientDTO();
               customer.setName(name);
               customer.setEmail(name+"@gmail.com");
               clientService.saveCustomer(customer);
           });
           clientService.listCustomers().forEach(customer->{
               try {
                   bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
                   bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());

               } catch (CustomerNotFoundException e) {
                   e.printStackTrace();
               }
           });
            List<CompteDTO> bankAccounts = bankAccountService.bankAccountList();
            for (CompteDTO bankAccount:bankAccounts){
                for (int i = 0; i <10 ; i++) {
                    String accountId;
                    if(bankAccount instanceof CompteEpargneDTO){
                        accountId=((CompteEpargneDTO) bankAccount).getId();
                    } else{
                        accountId=((CompteCourantDTO) bankAccount).getId();
                    }
                    operationService.credit(accountId,10000+Math.random()*120000,"Credit");
                    operationService.debit(accountId,1000+Math.random()*9000,"Debit");
                }
            }
        };
    }
    @Bean
    CommandLineRunner start(ClientRepository customerRepository,
                            CompteRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                Client customer=new Client();
                customer.setNom(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CompteCourant currentAccount=new CompteCourant();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setSolde(Math.random()*90000);
                currentAccount.setDateCreation(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setClient(cust);
                currentAccount.setDecouvert(9000);
                bankAccountRepository.save(currentAccount);

                CompteEpargne savingAccount=new CompteEpargne();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setSolde(Math.random()*90000);
                savingAccount.setDateCreation(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setClient(cust);
                savingAccount.setTauxInteret(5.5);
                bankAccountRepository.save(savingAccount);

            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i = 0; i <10 ; i++) {
                    Operation accountOperation=new Operation();
                    accountOperation.setDateOperation(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setTypeOperation(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setCompte(acc);
                    accountOperationRepository.save(accountOperation);
                }

            });
        };

    }

}
