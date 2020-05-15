package com.userRed.redesigned.service;

import com.userRed.redesigned.model.Dog;
import com.userRed.redesigned.model.Users;
import com.userRed.redesigned.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** @noinspection ALL*/
@Service
public class DogService {

	@Autowired
	private DogRepository repository;


	@Autowired
	MyUserDetailsService userService;

	public String saveDog(	Dog dogParam,
							String ownername) {
		Optional<Users> owner = userService.findByName(ownername);// gets by username
		dogParam.setOwner(owner.get());

		repository.save(dogParam);
		return dogParam.getName();
	}

    @Autowired
    MyUserDetailsService myUserDetailsService;

    public ResponseEntity<?> saveDog(Dog dog, String ownerName) {

        Optional<Users> owner = myUserDetailsService.findByUsername(ownerName);//gets by username
        dog.setOwner(owner.get());

        return ResponseEntity.ok(repository.save(dog));
    }


	public List<Dog> getDogs(String ownerUsername) {


		// get everthing from repo
		Optional<Users> owner = userService.findByName(ownerUsername);// gets by username
		// get's owner Id

		// uses repor
		long id = owner.get()
				.getId();
		List<Dog> ownersDogs = repository.findAllByOwner_Id(id);

		// return buildOptional(result)

		return ownersDogs;
	}

        //get everthing from repo
        Optional<Users> owner = myUserDetailsService.findByUsername(ownerUsername);//gets by username
        //get's owner Id

        //uses repor
        return repository.findAllByOwner_Id(owner.get().getId());
    }

    public ResponseEntity<?> updateNameOnDog(Long id, String name) {

        Optional<Dog> dog = repository.findByDogId(id);
        dog.get().setName(name);

        return ResponseEntity.ok(repository.save(dog.get()));
    }

    public ResponseEntity<?> updateAgeOnDog(Long id, int age) {
        if(age >= 25) return new ResponseEntity<>("Age should be lesser than 25", HttpStatus.NOT_ACCEPTABLE);

        Optional<Dog> dog = repository.findByDogId(id);
        dog.get().setAge(age);

        return ResponseEntity.ok(repository.save(dog.get()));
    }

    public ResponseEntity<?> updateBreedOnDog(Long id, String breed) {

        Optional<Dog> dog = repository.findByDogId(id);
        dog.get().setBreed(breed);

        return ResponseEntity.ok(repository.save(dog.get()));
    }

    public ResponseEntity<?> updateGenderOnDog(Long id, String gender) {

        Optional<Dog> dog = repository.findByDogId(id);
        dog.get().setGender(gender);

        return ResponseEntity.ok(repository.save(dog.get()));
    }

    public ResponseEntity<?> updateDescriptionOnDog(Long id, String description) {

        Optional<Dog> dog = repository.findByDogId(id);
        dog.get().setDescription(description);

        return ResponseEntity.ok(repository.save(dog.get()));
    }

    public Long deleteDogById(Long id) {
       return repository.deleteByDogId(id);
    }

    public Optional<Dog> findDogById(Long id) {
        return repository.findByDogId(id);

    }

}
