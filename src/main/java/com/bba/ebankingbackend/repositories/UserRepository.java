package com.bba.ebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bba.ebankingbackend.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserName(String username);
}
