package com.userRed.redesigned.controller;


import com.userRed.redesigned.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;


@RestController
@RequestMapping(path = "/dogs")
public class DogController {

    @Autowired
    private DogService service;

    //GET MAPPINGS

    /**CALL: localhost:8080/dogs/find?id=XXXX */

    @GetMapping(path = "/find", params = "id") // /dogs/find?id=id number
    public ResponseEntity<?> getDogById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(service.findDogById(id));
    }

    //PUT MAPPINGS

    /**CALL: localhost:8080/dogs/update?id=XXXX&name=XXXX */

    @PutMapping(path = "/update", params = {"id", "name"})
    public ResponseEntity<?> updateDogNameById(@RequestParam("id") Long id, @RequestParam("name") String name){
        return ResponseEntity.ok(service.updateNameOnDog(id, name));
    }

    /**CALL: localhost:8080/dogs/update?id=XXXX&age=XXXX */

    @PutMapping(path = "/update", params = {"id", "age"})
    public ResponseEntity<?> updateDogAgeById(@RequestParam("id") Long id, @RequestParam("age") int age){
        return ResponseEntity.ok(service.updateAgeOnDog(id, age));
    }

    /**CALL: localhost:8080/dogs/update?id=XXXX&breed=XXXX */

    @PutMapping(path = "/update", params = {"id", "breed"})
    public ResponseEntity<?> updateDogBreedById(@RequestParam("id") Long id, @RequestParam("breed") String breed){
        return ResponseEntity.ok(service.updateBreedOnDog(id, breed));
    }

    /**CALL: localhost:8080/dogs/update?id=XXXX&gender=XXXX */

    @PutMapping(path = "/update", params = {"id", "gender"})
    public ResponseEntity<?> updateDogGenderById(@RequestParam("id") Long id, @RequestParam("gender") String gender){
        return ResponseEntity.ok(service.updateGenderOnDog(id, gender));
    }

    /**CALL: localhost:8080/dogs/update?id=XXXX&description=XXXX */

    @PutMapping(path = "/update", params = {"id", "description"})
    public ResponseEntity<?> updateDogDescById(@RequestParam("id") Long id, @RequestParam("description") String description){
        return ResponseEntity.ok(service.updateDescriptionOnDog(id, description));
    }

    //DELETE MAPPINGS

    /**CALL: localhost:8080/dogs/delete?id=XXXX */

    @Transactional
    @DeleteMapping(path = "/delete", params = {"dogId"})
    public ResponseEntity<?> deleteDogById(@RequestParam("dogId") Long id) {
        return ResponseEntity.ok(service.deleteDogById((id)));
    }


}