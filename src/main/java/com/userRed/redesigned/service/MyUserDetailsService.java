package com.userRed.redesigned.service;

import com.userRed.redesigned.controller.MyUserDetails;
import com.userRed.redesigned.model.Users;
import com.userRed.redesigned.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("usrParam is created");
        return usersRepository.save(usrParam);
    }
}
