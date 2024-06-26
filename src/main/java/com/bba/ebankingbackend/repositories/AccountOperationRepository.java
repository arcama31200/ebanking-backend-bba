package com.bba.ebankingbackend.repositories;

import com.bba.ebankingbackend.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<Operation,Long> {
  List<Operation> findByCompteId(String accountId);
  Page<Operation> findByCompteIdOrderByDateOperationDesc(String accountId, Pageable pageable);
}
