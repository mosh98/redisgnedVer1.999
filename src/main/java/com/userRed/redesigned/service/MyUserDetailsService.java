package com.userRed.redesigned.service;


import com.userRed.redesigned.model.Users;
import com.userRed.redesigned.repository.UsersRepository;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;



@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;


    public Optional<Users> findByName(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public ResponseEntity<?> save(Users usrParam) throws Exception, HttpException {

        boolean mm = usersRepository.existsByUsername(usrParam.getUsername());
        System.out.println(mm);

        if(mm == true) return new ResponseEntity<>(HttpStatus.CONFLICT);

        int size = usrParam.getUsername().toCharArray().length;
        if(size >= 30) return new ResponseEntity<>("username cannot be bigger than or equal to 30",HttpStatus.NOT_ACCEPTABLE);


        usrParam.setCreatedAt();

       return ResponseEntity.ok(usersRepository.save(usrParam));
    }

    //was user before
/*    public Users save(Users usrParam) throws Exception, HttpException {


        usrParam.setCreatedAt();
        return usersRepository.save(usrParam);
    }*/

    public  String extractUsername(String smth){
        String[] parts = smth.split("@");
        return parts[0];
    }

    public ResponseEntity<?> saveUsingEmail(Users paramUsr){
        //should throw exception when paramUsr has null as mail'


        boolean s = paramUsr.getEmail().trim().isEmpty();
        if(s) return new ResponseEntity<>("ERROR: Email Cannot be empty",HttpStatus.UNAUTHORIZED);

        if(! paramUsr.getEmail().contains("@")) return new ResponseEntity<>("ERROR: Email has to be a valid email ", HttpStatus.UNAUTHORIZED);

        paramUsr.setCreatedAt();
        paramUsr.setUsername(extractUsername(paramUsr.getEmail()));

        return ResponseEntity.ok(usersRepository.save(paramUsr));
    }

    public Optional<Users> findUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

}
