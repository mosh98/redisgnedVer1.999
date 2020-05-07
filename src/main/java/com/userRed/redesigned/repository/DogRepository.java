package com.userRed.redesigned.repository;

import com.userRed.redesigned.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog,Integer> {
     Dog findByDogId(long id);

     Dog findByOwner(String owner);


}
