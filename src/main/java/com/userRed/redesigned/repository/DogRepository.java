package com.userRed.redesigned.repository;

import com.userRed.redesigned.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Integer> {
	Dog findByDogId(long id);

	Dog findByOwner(String owner);

	List<Dog> findAllByOwner_Id(long id);

}
