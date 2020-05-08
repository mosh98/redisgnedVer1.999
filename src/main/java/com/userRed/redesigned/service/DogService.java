package com.userRed.redesigned.service;

import com.userRed.redesigned.model.Dog;
import com.userRed.redesigned.model.Users;
import com.userRed.redesigned.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DogService {

    @Autowired
    private DogRepository repository;

    @Autowired
    MyUserDetailsService userService;

    public String saveDog(Dog dogParam, String ownername) {
        Optional<Users> owner = userService.findByName(ownername);//gets by username
        dogParam.setOwner(owner.get());

        repository.save(dogParam);
        return dogParam.getName();
    }

    public List<Dog> getDogs(String ownerUsername){

        //get everthing from repo
        Optional<Users> owner = userService.findByName(ownerUsername);//gets by username
        //get's owner Id

        //uses repor
        long id = owner.get().getId();
       List<Dog> ownersDogs = repository.findAllByOwner_Id(id);

        //return buildOptional(result)

        return ownersDogs;
    }
}
