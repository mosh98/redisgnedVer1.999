package com.userRed.redesigned.controller;

import com.userRed.redesigned.model.Dog;
import com.userRed.redesigned.model.Users;
import com.userRed.redesigned.service.DogService;
import com.userRed.redesigned.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UsersController {


    @Autowired
    MyUserDetailsService userService;

    @Autowired
    private DogService dogService;

    @GetMapping(path = "/find", params = "name")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        var result = userService.findByName(name);
        return ResponseEntity.of(result);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody Users users){
        return ResponseEntity.ok(userService.save(users));
    }

    /**How to call:
     * localhost:8080/user/dog/register?name=XXXX&breed=XXXX&age=XX&gender=XXXX&description=XXXX&owner=XXXXX
     * */

    @PostMapping(path = "dog/register", params = { "name","breed","age","gender","description","owner"})
    public ResponseEntity<?> register(@RequestParam String name,
                                      @RequestParam String breed,
                                      @RequestParam int age,
                                      @RequestParam String gender,
                                      @RequestParam String description,
                                      @RequestParam String owner) {
        Dog tmpDog = new Dog();
        tmpDog.setName(name);
        tmpDog.setAge(age);
        tmpDog.setBreed(breed);
        tmpDog.setGender(gender);
        tmpDog.setDescription(description);

        return ResponseEntity.ok(  dogService.saveDog(tmpDog, owner) + " is REGISTERED" );
    }
}
