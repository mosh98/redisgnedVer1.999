package com.userRed.redesigned.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.userRed.redesigned.model.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

	Authority findByName(String name);

}
