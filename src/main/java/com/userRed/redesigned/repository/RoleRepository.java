package com.userRed.redesigned.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.userRed.redesigned.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByName(String name);

}
