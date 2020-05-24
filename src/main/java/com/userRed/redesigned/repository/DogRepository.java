package com.userRed.redesigned.repository;

import com.userRed.redesigned.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DogRepository extends JpaRepository<Dog,Integer> {

     Optional<Dog> findByDogId(Long id);

     List<Dog> findAllByOwner_Id(Long id);

     Long deleteByDogId(Long id);
}
