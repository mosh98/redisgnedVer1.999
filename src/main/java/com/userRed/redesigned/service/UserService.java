package com.userRed.redesigned.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.userRed.redesigned.model.Authority;
import com.userRed.redesigned.model.Role;
import com.userRed.redesigned.model.User;
import com.userRed.redesigned.repository.RoleRepository;
import com.userRed.redesigned.repository.UserRepository;

import lombok.extern.java.Log;

@Log
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private FireBaseService fireBaseService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found.", username)));
	}

	public User loadUserByEmail(String email) throws UsernameNotFoundException {
		var user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Email %s not found.", email)));
		user.setAuthorities(getGrantedAuthorities(user));
		return user;
	}

	// Collect user roles and all authorities included in each role. User has role(s), role has authority(ies).
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
//		return userRepository.findAll();
		var users = userRepository.findAll();
		for(User user : users) {
			user.setAuthorities(getGrantedAuthorities(user));
		}
		return users;
	}

	public String findByUsername(String username) throws FirebaseAuthException {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			UserRecord ur = FirebaseAuth.getInstance()
					.getUser(user.get()
							.getUserId());
			log.info("Found user.");
			return ur.getEmail();
		}
		return "No user found";
	}

	public User registerNewUser(User user) throws FirebaseAuthException {

		if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
			// throw new IllegalStateException("User already exists.");
			log.warning("User with email " + user.getEmail() + " already exists");
//			return null;
			throw new SecurityException(String.format("User with email %s already exists", user.getEmail()));
		}

		if (fireBaseService.existsByEmail(user.getEmail())) {
			// throw new IllegalStateException
			log.warning("User with email " + user.getEmail() + " already exists");
//			return null;
			throw new SecurityException(String.format("User with email %s already exists", user.getEmail()));
		}

		String userId = registerNewFireBaseUser(user);
		user.setUserId(userId)
//				.setRole(new Role("ADMIN"))  //Authority.ADMIN_READ)
				// .setGrantedAuthorities(Role.ADMIN.getGrantedAuthorities())
				.setPassword(passwordEncoder.encode(user.getPassword()))
				.setAccountNonExpired(true)
				.setAccountNonLocked(true)
				.setCredentialsNonExpired(true)
				.setEnabled(true)
			//	Role role = roleRepository.findByName(name);

				.setRoles(roleRepository.findByName("ROLE_USER"));
				
		userRepository.save(user);
		return user;
	}

	private String registerNewFireBaseUser(User user) throws FirebaseAuthException {

		CreateRequest request = new CreateRequest().setEmail(user.getEmail())
				.setEmailVerified(false)
				.setPassword(user.getPassword())
//				.setPhoneNumber(user.getPhoneNumber() == null
//						? ""
//						: user.getPhoneNumber())
				.setDisplayName(user.getUsername())
//				.setPhotoUrl(user.getPhotoUrl() == null
//						? ""
//						: user.getPhotoUrl())
				.setDisabled(false);

		UserRecord userRecord = FirebaseAuth.getInstance()
				.createUser(request);
		System.out.println("Successfully created new user: " + userRecord.getUid());

		return userRecord.getUid();
	}
}
