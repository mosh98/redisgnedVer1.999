package com.userRed.redesigned.repository;

import com.userRed.redesigned.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findById(Long id);

	Optional<Users> findByUsername(String username);

	// Users findByUsername(String username);

	Optional<Users> findByName(String username);

	Optional<Users> findByEmail(String email);

}
