package com.userRed.redesigned.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userRed.redesigned.firebase.FireBaseAuthenticationToken;
import com.userRed.redesigned.model.Dog;
import com.userRed.redesigned.repository.DogRepository;
import com.userRed.redesigned.repository.UserRepository;
import com.userRed.redesigned.service.DogService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(path = "/dogs")
public class DogController {

	@Autowired
	private DogService service;

	@Autowired
	private DogRepository dogRepository;

	@Autowired
	private UserRepository userRepository;
	
	@PreAuthorize("#username == authentication.principal.username")
	@GetMapping(path = "/{username}/{dogname}")
	public ResponseEntity<?> getDog(FireBaseAuthenticationToken authentication,
									@PathVariable String username,
									@PathVariable String dogname) {
//		var user = userRepository.findByUsername(username);
//		if(user.isPresent() &&
//			user.get().hasDog(dogname)) {
//		}
	
		var dog = dogRepository.findByName(dogname);
		if (dog.isPresent()) {
			if (getOwnerUsername(dog.get()).equals(username)) {
				log.info(username + " is owner of " + dogname);
				return ResponseEntity.of(dog);
			}
		}
		return ResponseEntity.badRequest()
				.body(String.format("%s is not the owner of %s", username, dogname));
	}

	private String getOwnerUsername(Dog dog) {
		return dog.getOwner()
				.getUsername();
	}

	// GET MAPPINGS

	/** CALL: localhost:8080/dogs/find?id=XXXX */

//	@GetMapping(path = "/find", params = "id") // /dogs/find?id=id number
//	public ResponseEntity<?> getDogById(@RequestParam("id") Long id) {
//		return ResponseEntity.ok(service.findDogById(id));
//	}
//
//	// PUT MAPPINGS
//
//	/** CALL: localhost:8080/dogs/update/id?=XXXX&name=XXXX */
//
//	@PutMapping(path = "/update", params = { "id", "name" })
//	public ResponseEntity<?> updateDogNameById(	@RequestParam("id") Long id,
//												@RequestParam("name") String name) {
//		return ResponseEntity.ok(service.updateNameOnDog(id, name));
//	}
//
//	/** CALL: localhost:8080/dogs/update/id?=XXXX&age=XXXX */
//
////	@PutMapping(path = "/update", params = { "id", "age" })
////	public ResponseEntity<?> updateDogAgeById(	@RequestParam("id") Long id,
////												@RequestParam("age") int age) {
////		return ResponseEntity.ok(service.updateAgeOnDog(id, age));
////	}
//
//	/** CALL: localhost:8080/dogs/update/id?=XXXX&breed=XXXX */
//
//	@PutMapping(path = "/update", params = { "id", "breed" })
//	public ResponseEntity<?> updateDogBreedById(@RequestParam("id") Long id,
//												@RequestParam("breed") String breed) {
//		return ResponseEntity.ok(service.updateBreedOnDog(id, breed));
//	}
//
//	/** CALL: localhost:8080/dogs/update/id?=XXXX&gender=XXXX */
//
//	@PutMapping(path = "/update", params = { "id", "gender" })
//	public ResponseEntity<?> updateDogGenderById(	@RequestParam("id") Long id,
//													@RequestParam("gender") Gender gender) {
//		return ResponseEntity.ok(service.updateGenderOnDog(id, gender));
//	}
//
//	/** CALL: localhost:8080/dogs/update/id?=XXXX&description=XXXX */
//
//	@PutMapping(path = "/update", params = { "id", "description" })
//	public ResponseEntity<?> updateDogDescById(	@RequestParam("id") Long id,
//												@RequestParam("description") String description) {
//		return ResponseEntity.ok(service.updateDescriptionOnDog(id, description));
//	}
//
//	// DELETE MAPPINGS
//
//	/** CALL: localhost:8080/dogs/delete?id=XXXX */
//
//	@Transactional
//	@DeleteMapping(path = "/delete", params = { "dogId" })
//	public ResponseEntity<?> deleteDogById(@RequestParam("dogId") Long id) {
//		return ResponseEntity.ok(service.deleteDogById((id)));
//	}

}