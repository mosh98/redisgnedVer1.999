package com.userRed.redesigned.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuthException;
import com.userRed.redesigned.model.Dog;
import com.userRed.redesigned.model.User;
import com.userRed.redesigned.request.UserRequest;
import com.userRed.redesigned.service.DogService;
import com.userRed.redesigned.service.UserService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	UserService userService;

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
    public ResponseEntity<?> registerUser(@RequestBody User users) throws Exception {
        return userService.save(users);
    }

    /**CALL: localhost:8080/user/registerWithMail */

    @PostMapping(path= "/registerWithMail")
    public ResponseEntity<?> registerUsingMail(@RequestBody User users){
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
//        /** GET : http://localhost:8080/user/query?username=XXX*/
//    @GetMapping("/query")
//    public Page<Users> getAllByQuery(
//            @RequestParam(value = "username", required = false) String username,
//            Pageable pageable) {
//        return  userService.getByQuery(username, pageable);
//    }

//	@GetMapping
////	@PreAuthorize ("hasRole('ROLE_WRITE')")
//	public String getAllUsers(Principal principal) {
//		System.out.println("/USER");
//		return "Hej"; // + principal.getName();
//	}
	
//	@PostMapping(path = "/register")
//	public ResponseEntity<?> registerNewUser(@RequestBody UserRequest request) throws FirebaseAuthException {
//		System.out.println("registering new username= " + request.getUsername() + " password= " + request.getPassword()
//				+ " email= " + request.getEmail());
//
//		var req = new User().setUsername(request.getUsername())
//				.setPassword(request.getPassword())
//				.setEmail(request.getEmail())
//				.setRoles(new Role("ROLE_USER"));
//		var user = (userService.registerNewUser(req));
//		var result = Optional.ofNullable(user);
//		return ResponseEntity.of(result);
//	}

	@GetMapping
	public ResponseEntity<?> getAllUsers(Principal principal) throws FirebaseAuthException {
		log.info("Current user= " + principal.getName());
		return ResponseEntity.ok(userService.getAllUsers());
		
//		if(principal != null) {
//			System.out.println("Current user= " + principal.getName());
//		}
//		
////		logger.info("'GET /user' by User=" + principal.getName());
//
//		ListUsersPage page = FirebaseAuth.getInstance()
//				.listUsers(null);
//
//		List<String> userIds = new ArrayList<>();
//
//		for (ExportedUserRecord user : page.iterateAll()) {
//			System.out.println("UID: " + user.getUid() + " USERNAME: " + user.getDisplayName());
//			userIds.add(user.getUid());
//		}
//
//		return ResponseEntity.ok(userIds);
	}

	@PostMapping(path = "/add")
	public ResponseEntity<?> registerNewUser1(@RequestBody UserRequest request) throws FirebaseAuthException {
		log.info("registering new username= " + request.getUsername() + " password= " + request.getPassword()
				+ " email= " + request.getEmail());

		var req = new User().setUsername(request.getUsername())
				.setPassword(request.getPassword())
				.setEmail(request.getEmail());
//				.setRoles("ROLE_USER");
		var user = (userService.registerNewUser(req));
		var result = Optional.ofNullable(user);
		return ResponseEntity.of(result);
	}
}
