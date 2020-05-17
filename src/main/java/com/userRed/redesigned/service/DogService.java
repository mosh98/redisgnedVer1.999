package com.userRed.redesigned.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userRed.redesigned.model.Dog;
import com.userRed.redesigned.repository.DogRepository;

/** @noinspection ALL */
@Service
public class DogService {

	@Autowired
	private DogRepository dogRepository;

//	@Autowired
//	UserService myUserDetailsService;

	public Dog save(Dog dog) {
		return dogRepository.save(dog);
	}

	public Long deleteById(Long id) {
		return dogRepository.deleteById(id);
	}
	
	public Optional<Dog> findByName(String name){
		return dogRepository.findByName(name);
	}
	
//	public ResponseEntity<?> saveDog(	Dog dog,
//										String ownerName) {
//
//		Optional<User> owner = myUserDetailsService.findByUsername(ownerName);// gets by username
//		dog.setOwner(owner.get());
//
//		return ResponseEntity.ok(repository.save(dog));
//	}

//	public List<Dog> getDogs(String ownerUsername) {
//
//		// get everthing from repo
//		Optional<User> owner = myUserDetailsService.findByUsername(ownerUsername);// gets by username
//		// get's owner Id
//
//		// uses repor
//		return repository.findAllByOwner_Id(owner.get()
//				.getId());
//	}

//	public ResponseEntity<?> updateNameOnDog(	Long id,
//												String name) {
//
//		Optional<Dog> dog = repository.findById(id);
//		dog.get()
//				.setName(name);
//
//		return ResponseEntity.ok(repository.save(dog.get()));
//	}
//
////	public ResponseEntity<?> updateAgeOnDog(Long id,
////											int age) {
////		if (age >= 25)
////			return new ResponseEntity<>("Age should be lesser than 25", HttpStatus.NOT_ACCEPTABLE);
////
////		Optional<Dog> dog = repository.findById(id);
////		dog.get()
////				.setAge(age);
////
////		return ResponseEntity.ok(repository.save(dog.get()));
////	}
//
//	public ResponseEntity<?> updateBreedOnDog(	Long id,
//												String breed) {
//
//		Optional<Dog> dog = repository.findById(id);
//		dog.get()
//				.setBreed(breed);
//
//		return ResponseEntity.ok(repository.save(dog.get()));
//	}
//
//	public ResponseEntity<?> updateGenderOnDog(	Long id,
//												Gender gender) {
//
//		Optional<Dog> dog = repository.findById(id);
//		dog.get()
//				.setGender(gender);
//
//		return ResponseEntity.ok(repository.save(dog.get()));
//	}
//
//	public ResponseEntity<?> updateDescriptionOnDog(Long id,
//													String description) {
//
//		Optional<Dog> dog = repository.findById(id);
//		dog.get()
//				.setDescription(description);
//
//		return ResponseEntity.ok(repository.save(dog.get()));
//	}
//
//	public Long deleteDogById(Long id) {
//		return repository.deleteById(id);
//	}
//
//	public Optional<Dog> findDogById(Long id) {
//		return repository.findById(id);
//
//	}
}
