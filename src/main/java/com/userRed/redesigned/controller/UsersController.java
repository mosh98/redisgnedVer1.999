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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


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

/*

    @Autowired
    private AuthenticationManager authenticationManager;
*/


    @Autowired
    MyUserDetailsService userService;



    @Autowired
    private DogService dogService;

    // POST MAPPINGS

    @PostMapping(path = "/login", params = { "username", "password"})
    public ResponseEntity<?>  login(@RequestParam String username, @RequestParam String password) throws Exception {



        // val token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok( userService.verify(username,password));
    }

/*
    private void authenticate(String username, String password) throws Exception {
        try {
              authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }*/

    /**CALL: localhost:8080/user/register */

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerUser(@RequestBody Users users) throws Exception {
        return userService.save(users);
    }

    /**CALL: localhost:8080/user/registerWithMail */

    @PostMapping(path= "/registerWithMail")
    public ResponseEntity<?> registerUsingMail(@RequestBody Users users){
        return userService.saveUsingEmail(users);
    }

    /**CALL: localhost:8080/user/dog/register?owner=XXXXX */

    @PostMapping(path = "dog/register", params = {"owner"})
    public ResponseEntity<?> register(@RequestBody Dog dog, @RequestParam String owner) {

        return dogService.saveDog(dog, owner);
    }




    // PUT MAPPINGS

    /**CALL: localhost:8080/user/update?username=XXXX&&description=XXXXX */

    @PutMapping(path = "update", params = {"username", "description"})
    public ResponseEntity<?> updateDescription(@RequestParam("username") String username,@RequestParam("description") String description) {

        return ResponseEntity.ok(userService.updateUserDescription(username, description));

    }

    /**CALL: localhost:8080/user/update?username=XXXX&&password=XXXXX */

    @PutMapping(path = "update", params = {"username", "password"})
    public ResponseEntity<?> updatePassword(@RequestParam("username") String username,@RequestParam("password") String password) {

        return ResponseEntity.ok(userService.updatePassword(username, password));

    }

    /**CALL: localhost:8080/user/update?username=XXXX&&dateofbirth=XXXXX */

    @PutMapping(path = "update", params = {"username", "date_of_birth"})
    public ResponseEntity<?> updateDateOfBirth(@RequestParam("username") String username,@RequestParam("date_of_birth") String date_of_birth) {

        return ResponseEntity.ok(userService.updateDateOfBirth(username, date_of_birth));

    }

    /**CALL: localhost:8080/user/update?username=XXXX&&email=XXXXX */

    @PutMapping(path = "update", params = {"username", "email"})
    public ResponseEntity<?> updateEmail(@RequestParam("username") String username,@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.updateEmail(username, email));
    }

    // GET MAPPINGS

    /**CALL: localhost:8080/user/getMyDogs?username=XXXX */

    @GetMapping(path = "/getMyDogs", params = {"username"}) //username for owner that is
    public ResponseEntity<?> getMyDogs(@RequestParam String username){
        return ResponseEntity.ok(dogService.getDogs(username));
    }

    /**CALL: localhost:8080/user/find?username=XXXX */

    @GetMapping(path = "/find", params = "username")
    public ResponseEntity<?> findByName(@RequestParam String username) {

        var result = userService.findByUsername(username);
        return ResponseEntity.of(result);

    }

    /**CALL: localhost:8080/user/find?email=XXXX */

    @GetMapping(path = "/find", params = "email")
    public ResponseEntity<?> findUsingEmail(@RequestParam String email) {
        var result = userService.findUserByEmail(email);
        return ResponseEntity.of(result);
    }
        /** GET : http://localhost:8080/user/query?username=XXX*/
    @GetMapping("/query")
    public Page<Users> getAllByQuery(
            @RequestParam(value = "username", required = false) String username,
            Pageable pageable) {
        return  userService.getByQuery(username, pageable);
    }








}
