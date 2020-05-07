package com.userRed.redesigned.service;

import com.userRed.redesigned.controller.MyUserDetails;
import com.userRed.redesigned.model.Users;
import com.userRed.redesigned.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
        Optional<Users> optionalUsers = usersRepository.findByName(username);

        optionalUsers
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return optionalUsers
                .map(MyUserDetails::new).get();
    }



    public Users save(Users usrParam){
        usrParam.setCreatedAt();
        return usersRepository.save(usrParam);
    }

    public  String extractUsername(String smth){
        String[] parts = smth.split("@");
        return parts[0];
    }

    public Users saveUsingEmail(Users paramUsr){
        paramUsr.setCreatedAt();
        paramUsr.setUsername(extractUsername(paramUsr.getEmail()));
        return usersRepository.save(paramUsr);
    }

    public Optional<Users> findUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

}
