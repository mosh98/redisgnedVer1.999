package com.userRed.redesigned.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;

import com.userRed.redesigned.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public List<User> findAll();
	
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByEmail(String email);
	
	public User save(User user);

	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);


    @Query("select m from User m where " +
            "(?1 is null or upper(m.username) like concat('%', upper(?1), '%')) ")
    Page<User> getByQuery(String name, final Pageable pageable);

    @Query("select m from User m where " +
            "(?1 is null or upper(m.username) like concat('%', upper(?1), '%')) ")
    Page<User> findAllByUsername(String name, final Pageable pageable);
}
