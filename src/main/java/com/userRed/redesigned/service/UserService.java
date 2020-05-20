package com.userRed.redesigned.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Bucket;
import com.google.firebase.auth.FirebaseAuthException;
import com.userRed.redesigned.cloudstorage.CloudStorageService;
import com.userRed.redesigned.model.Authority;
import com.userRed.redesigned.model.Dog;
import com.userRed.redesigned.model.Role;
import com.userRed.redesigned.model.User;
import com.userRed.redesigned.repository.RoleRepository;
import com.userRed.redesigned.repository.UserRepository;

import lombok.val;
import lombok.extern.java.Log;

@Log
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private DogService dogService;

	@Autowired
	private FireBaseService fireBaseService;

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Autowired
	private CloudStorageService cloudStorageService;

	@Autowired
	ObjectMapper mapper;

	public Optional<User> getUser(@Valid @NotBlank String username) {
		return userRepository.findByUsername(username);
	}

	public User getAuthenticatedUser(String username) {
		return getUser(username).orElseThrow(() -> new UsernameNotFoundException(
				String.format("User %s is authenticated but could not be found in user database.", username)));
	}

	public Dog saveDog(	@Valid User user,
						@Valid Dog dog) {
		dog.setOwner(user);
		if (user.hasDog(dog)) {
			log.info("Updating existing dog");
			val userDog = user.getDog(dog.getName());
			val id = userDog.getId();
			dog.setId(id);
		} else {
			log.info("Creating new dog");
		}
		return dogService.save(dog);
	}

	public User removeDog(	@Valid User user,
							Dog dog)
			throws HttpException,
			Exception {
		if (user.removeDog(dog.getName())) {
			log.info("Dog deleted and removed from user");
			return save(user);
		}
		log.warning("Dog not owned by user therefor not removable.");
		return null;
	}

//	public ResponseEntity<?> verify(String usrname,
//									String password) {
//
//		Optional<User> ussr = findByUsername(usrname);
//		String encryptedPass = passwordEncoder.encode(password);
//
//		if (ussr.get()
//				.getPassword()
//				.equals(encryptedPass)) {
//			return ResponseEntity.ok("User Granted");
//		}
//
//		return ResponseEntity.of(ussr);
//	}

	public User save(User user) throws Exception,
			HttpException {
		if (!userRepository.existsByUsername(user.getUsername())) {
			user.setCreatedAt(LocalDate.now());
		}
		return userRepository.save(user);
	}

	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User loadUserByUsername(@Valid @NotBlank String username) throws UsernameNotFoundException {
		var user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(
						String.format("User with username %s not found.", username)));
		user.setAuthorities(getGrantedAuthorities(user));
		return user;
	}

	public User loadUserByEmail(@Valid @NotBlank @Email String email) throws UsernameNotFoundException {
		var user = userRepository.findByEmail(email)
				.orElseThrow(
						() -> new UsernameNotFoundException(String.format("User with email %s not found.", email)));
		user.setAuthorities(getGrantedAuthorities(user));
		return user;
	}

	// Collect user roles and all authorities included in each role. User has
	// role(s), role has authority(ies).
	private Collection<SimpleGrantedAuthority> getGrantedAuthorities(User user) {
		var authorities = new HashSet<SimpleGrantedAuthority>();
		for (Role role : user.getRoles()) {
			for (Authority authority : role.getAuthorities()) {
				authorities.add(new SimpleGrantedAuthority(authority.getName()));
			}
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	public List<User> getAllUsers() {
		var name = (User) SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();

		System.out.println(name.getEmail());

//		return userRepository.findAll();
		var users = userRepository.findAll();
		for (User user : users) {
			user.setAuthorities(getGrantedAuthorities(user));
		}
		return users;
	}

//	public String findByUsername(String username) throws FirebaseAuthException {
//		Optional<User> user = userRepository.findByUsername(username);
//		if (user.isPresent()) {
//			UserRecord ur = FirebaseAuth.getInstance()
//					.getUser(user.get()
//							.getUserId());
//			log.info("Found user.");
//			return ur.getEmail();
//		}
//		return "No user found";
//	}

	public User registerNewUser(User user) throws FirebaseAuthException {
		if (existsByUsernameOrEmail(user)){
			log.warning("User with email " + user.getEmail() + " already exists");
			throw new SecurityException(String
					.format("User with username %s and email %s already exists", user.getUsername(), user.getEmail()));
		}
		if(!fireBaseService.existsByEmail(user.getEmail())){
			throw new SecurityException("User not registered in Firebase.");
		}

		
//		CreateRequest request = new CreateRequest().setEmail(user.getEmail())
//				.setEmailVerified(false)
//				.setDisplayName(user.getUsername())
//				.setDisabled(false);
//		UserRecord userRecord = FirebaseAuth.getInstance()
//				.createUser(request);
//		System.out.println("Successfully created new user: " + userRecord.getUid());
//		String userId = userRecord.getUid();
		String userId= fireBaseService.getUserIdByEmail(user.getEmail());
		
		Bucket bucket = cloudStorageService.createRandomBucket();
		
		user.setUserId(userId)
				.setAccountNonExpired(true)
				.setAccountNonLocked(true)
				.setCredentialsNonExpired(true)
				.setEnabled(true)
				.setRoles(roleRepository.findByName("ROLE_USER"))
				.setBucket(bucket.toString());
		userRepository.save(user);
		System.out.println(user.toString());
		
		return user;
	}

	private boolean existsByUsernameOrEmail(User user) {
		return userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail());
		
		//		|| fireBaseService.existsByEmail(user.getEmail());

	}

//	private String registerNewFireBaseUser(User user) throws FirebaseAuthException {
//
//		CreateRequest request = new CreateRequest().setEmail(user.getEmail())
//				.setEmailVerified(false)
//				.setDisplayName(user.getUsername())
//				.setDisabled(false);
//
//		UserRecord userRecord = FirebaseAuth.getInstance()
//				.createUser(request);
//		System.out.println("Successfully created new user: " + userRecord.getUid());
//
//		return userRecord.getUid();
//	}
}
