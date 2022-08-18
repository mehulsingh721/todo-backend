package com.todo.todo.repository;

import org.springframework.stereotype.Repository;

import com.todo.todo.domain.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String>{
	AppUser findByUsername(String username);
}
