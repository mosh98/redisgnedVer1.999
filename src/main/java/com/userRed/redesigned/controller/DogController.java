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


    @GetMapping(path = "/find", params = "id") // /dogs/find?id=id number
    public ResponseEntity<?> getDogById(@RequestParam("id") int id) {
        return ResponseEntity.ok(service.findDogById(id));
    }

/*
    @PutMapping(path = "/update") // /dogs/update
    public ResponseEntity<?> updateDogById(@RequestBody Dog dog) {
        return ResponseEntity.ok(service.updateDog(dog));
    }*/


    @DeleteMapping(path = "/delete", params = "id") // /dogs/delete?id=id number
    @Transactional
    public ResponseEntity<?> deleteDogById(@RequestParam("id") int id) {

        long x = id;
        return ResponseEntity.ok( service.deleteDogById( id ) );
    }


}