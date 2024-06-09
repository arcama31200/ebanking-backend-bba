package com.bba.ebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bba.ebankingbackend.entities.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select c from Customer c where c.name like :kw")
    List<Client> searchCustomer(@Param("kw") String keyword);
}
