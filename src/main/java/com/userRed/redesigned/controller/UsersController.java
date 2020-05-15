package com.userRed.redesigned.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userRed.redesigned.model.Dog;
import com.userRed.redesigned.model.Users;
import com.userRed.redesigned.service.DogService;
import com.userRed.redesigned.service.MyUserDetailsService;

@RestController
@RequestMapping(path = "/user")
public class UsersController {

	@Autowired
	MyUserDetailsService userService;

	@Autowired
	private DogService dogService;

	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		return ResponseEntity.ok(userService.findAll());
	}

	@GetMapping(path = "/find", params = "name")
	public ResponseEntity<?> findByName(@RequestParam String name) {

		var result = userService.findByName(name);

		return ResponseEntity.of(result);

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody Users users) {
		return ResponseEntity.ok(userService.save(users));
	}

	@RequestMapping(path = "/registerWithMail", method = RequestMethod.POST)
	public ResponseEntity<?> registerUsingMail(@RequestBody Users users) {

		return ResponseEntity.ok(userService.saveUsingEmail(users));
	}

	/*
	 * @GetMapping(path = "/find", params = "email") public ResponseEntity<?>
	 * findUsingEmail(@RequestParam String email) { var result =
	 * userService.findUserByEmail(email); return ResponseEntity.of(result); }
	 */

	/**
	 * How to call:
	 * localhost:8080/user/dog/register?name=XXXX&breed=XXXX&age=XX&gender=XXXX&description=XXXX&owner=XXXXX
	 */

	@PostMapping(path = "dog/register", params = { "name", "breed", "age", "gender", "description", "owner" })
	public ResponseEntity<?> register(	@RequestParam String name,
										@RequestParam String breed,
										@RequestParam int age,
										@RequestParam String gender,
										@RequestParam String description,
										@RequestParam String owner) {
		Dog tmpDog = new Dog();
		tmpDog.setName(name);
		tmpDog.setAge(age);
		tmpDog.setBreed(breed);
		tmpDog.setGender(gender);
		tmpDog.setDescription(description);

		return ResponseEntity.ok(dogService.saveDog(tmpDog, owner) + " is REGISTERED");
	}

	@GetMapping(path = "/getMyDogs", params = { "username" }) // username for owner that is
	public ResponseEntity<?> getMyDogs(@RequestParam String username) {
		return ResponseEntity.ok(dogService.getDogs(username));
	}

}
