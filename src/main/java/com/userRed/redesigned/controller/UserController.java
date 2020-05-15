package com.userRed.redesigned.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuthException;
import com.userRed.redesigned.model.Role;
import com.userRed.redesigned.model.User;
import com.userRed.redesigned.request.UserRequest;
import com.userRed.redesigned.service.UserService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	UserService userService;
	
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
