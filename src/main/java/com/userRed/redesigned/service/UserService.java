package com.userRed.redesigned.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    ObjectMapper mapper;



    public ResponseEntity<?> verify(String usrname, String password){

        Optional<User> ussr = findByUsername(usrname);
        String encryptedPass = passwordEncoder.encode( password );

        if(ussr.get().getPassword().equals(encryptedPass)){
            return ResponseEntity.ok("User Granted");
        }


        return ResponseEntity.of(ussr);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        return null;
//    }


    public ResponseEntity<?> save(User usrParam) throws Exception, HttpException {

        boolean existsByUserName = userRepository.existsByUsername(usrParam.getUsername());
        System.out.println(existsByUserName);

        if(existsByUserName) return new ResponseEntity<>(HttpStatus.CONFLICT);

        int size = usrParam.getUsername().toCharArray().length;
        if(size >= 30) return new ResponseEntity<>("username cannot be bigger than or equal to 30",HttpStatus.NOT_ACCEPTABLE);

        String encryptedPass = passwordEncoder.encode( usrParam.getPassword() );

        usrParam.setCreatedAt();
        usrParam.setPassword(encryptedPass);
        usrParam.setDescription("Write something about yourself!");
        return ResponseEntity.ok(userRepository.save(usrParam));
    }

    private String extractUsername(String smth){
        String[] parts = smth.split("@");
        return parts[0];
    }

    public ResponseEntity<?> saveUsingEmail(User paramUsr){
        //should throw exception when paramUsr has null as mail

        boolean emailEmpty = paramUsr.getEmail().trim().isEmpty();
        if(emailEmpty)
            return new ResponseEntity<>("ERROR: Email Cannot be empty",HttpStatus.UNAUTHORIZED);

        if(! paramUsr.getEmail().contains("@"))
            return new ResponseEntity<>("ERROR: Email has to be a valid email ", HttpStatus.UNAUTHORIZED);

        paramUsr.setCreatedAt();
        paramUsr.setDescription("Write something about yourself!");
        paramUsr.setUsername(extractUsername(paramUsr.getEmail()));

        return ResponseEntity.ok(userRepository.save(paramUsr));
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<?> updateUserDescription(String username, String description) {

        Optional<User> user = userRepository.findByUsername(username);
        user.get().setDescription(description);

        return ResponseEntity.ok(userRepository.save(user.get()));
    }

    public ResponseEntity<?> updatePassword(String username, String password) {

        Optional<User> user = userRepository.findByUsername(username);
        user.get().setPassword(password);

        return ResponseEntity.ok(userRepository.save(user.get()));
    }

    public ResponseEntity<?> updateDateOfBirth(String username, String date_of_birth) {

        Optional<User> user = userRepository.findByUsername(username);
        user.get().setDate_of_birth(date_of_birth);

        return ResponseEntity.ok(userRepository.save(user.get()));
    }

    public ResponseEntity<?> updateEmail(String username, String email) {

        Optional<User> user = userRepository.findByUsername(username);

        if(!email.contains("@"))
            return new ResponseEntity<>("ERROR: Email has to be a valid email ", HttpStatus.UNAUTHORIZED);

        user.get().setEmail(email);
        return ResponseEntity.ok(userRepository.save(user.get()));
    }

//    public Page<User> getByQuery(String name, Pageable pageable) {
//       //return usersRepository.findAllByUsername(name, pageable).map(Users::new);
//       return userRepository.getByQuery(name, pageable).map(User::new);
//    }

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
