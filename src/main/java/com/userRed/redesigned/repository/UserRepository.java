package com.userRed.redesigned.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.userRed.redesigned.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public List<com.userRed.redesigned.model.User> findAll();

	public Optional<User> findByUsername(String username);

	public Optional<User> findByEmail(String email);

	public User save(User user);

	public boolean existsByUsername(String username);

	public boolean existsByEmail(String email);
}
