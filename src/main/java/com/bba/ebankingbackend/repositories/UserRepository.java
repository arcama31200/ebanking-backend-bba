package com.bba.ebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bba.ebankingbackend.entities.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long>{
	AppUser findByUsername(String username);
	boolean existsByUsername(String username);
}
