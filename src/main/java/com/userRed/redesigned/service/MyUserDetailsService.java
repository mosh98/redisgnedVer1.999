//package com.userRed.redesigned.service;
//
//
//import java.util.Optional;
//
//import org.apache.http.HttpException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.userRed.redesigned.model.User;
//import com.userRed.redesigned.repository.UserRepository;
//
///** @noinspection OptionalGetWithoutIsPresent*/
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder bcryptEncoder;
//
//    @Autowired
//    ObjectMapper mapper;
//
//
//
//    public ResponseEntity<?> verify(String usrname, String password){
//
//        Optional<User> ussr = findByUsername(usrname);
//        String encryptedPass = bcryptEncoder.encode( password );
//
//        if(ussr.get().getPassword().equals(encryptedPass)){
//            return ResponseEntity.ok("User Granted");
//        }
//
//
//        return ResponseEntity.of(ussr);
//    }
//
//    public Optional<User> findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        return null;
//    }
//
//
//    public ResponseEntity<?> save(User usrParam) throws Exception, HttpException {
//
//        boolean existsByUserName = userRepository.existsByUsername(usrParam.getUsername());
//        System.out.println(existsByUserName);
//
//        if(existsByUserName) return new ResponseEntity<>(HttpStatus.CONFLICT);
//
//        int size = usrParam.getUsername().toCharArray().length;
//        if(size >= 30) return new ResponseEntity<>("username cannot be bigger than or equal to 30",HttpStatus.NOT_ACCEPTABLE);
//
//        String encryptedPass = bcryptEncoder.encode( usrParam.getPassword() );
//
//        usrParam.setCreatedAt();
//        usrParam.setPassword(encryptedPass);
//        usrParam.setDescription("Write something about yourself!");
//        return ResponseEntity.ok(userRepository.save(usrParam));
//    }
//
//    private String extractUsername(String smth){
//        String[] parts = smth.split("@");
//        return parts[0];
//    }
//
//    public ResponseEntity<?> saveUsingEmail(User paramUsr){
//        //should throw exception when paramUsr has null as mail
//
//        boolean emailEmpty = paramUsr.getEmail().trim().isEmpty();
//        if(emailEmpty)
//            return new ResponseEntity<>("ERROR: Email Cannot be empty",HttpStatus.UNAUTHORIZED);
//
//        if(! paramUsr.getEmail().contains("@"))
//            return new ResponseEntity<>("ERROR: Email has to be a valid email ", HttpStatus.UNAUTHORIZED);
//
//        paramUsr.setCreatedAt();
//        paramUsr.setDescription("Write something about yourself!");
//        paramUsr.setUsername(extractUsername(paramUsr.getEmail()));
//
//        return ResponseEntity.ok(userRepository.save(paramUsr));
//    }
//
//    public Optional<User> findUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    public ResponseEntity<?> updateUserDescription(String username, String description) {
//
//        Optional<User> user = userRepository.findByUsername(username);
//        user.get().setDescription(description);
//
//        return ResponseEntity.ok(userRepository.save(user.get()));
//    }
//
//    public ResponseEntity<?> updatePassword(String username, String password) {
//
//        Optional<User> user = userRepository.findByUsername(username);
//        user.get().setPassword(password);
//
//        return ResponseEntity.ok(userRepository.save(user.get()));
//    }
//
//    public ResponseEntity<?> updateDateOfBirth(String username, String date_of_birth) {
//
//        Optional<User> user = userRepository.findByUsername(username);
//        user.get().setDate_of_birth(date_of_birth);
//
//        return ResponseEntity.ok(userRepository.save(user.get()));
//    }
//
//    public ResponseEntity<?> updateEmail(String username, String email) {
//
//        Optional<User> user = userRepository.findByUsername(username);
//
//        if(!email.contains("@"))
//            return new ResponseEntity<>("ERROR: Email has to be a valid email ", HttpStatus.UNAUTHORIZED);
//
//        user.get().setEmail(email);
//        return ResponseEntity.ok(userRepository.save(user.get()));
//    }
//
////    public Page<User> getByQuery(String name, Pageable pageable) {
////       //return usersRepository.findAllByUsername(name, pageable).map(Users::new);
////       return userRepository.getByQuery(name, pageable).map(User::new);
////    }
//
//
//}
