package com.userRed.redesigned.controller;

import com.userRed.redesigned.model.Dog;
import com.userRed.redesigned.model.Users;
import com.userRed.redesigned.repository.UsersRepository;
import com.userRed.redesigned.service.DogService;
import com.userRed.redesigned.service.MyUserDetailsService;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UsersController {


    @Autowired
    MyUserDetailsService userService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private DogService dogService;

    @GetMapping(path = "/find", params = "username")
    public ResponseEntity<?> findByName(@RequestParam String username) {

        var result = userService.findByName(username);

        return ResponseEntity.of(result);

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody Users users) throws Exception {
        return userService.save(users);
    }

    @RequestMapping(path= "/registerWithMail", method = RequestMethod.POST)
    public ResponseEntity<?> registerUsingMail(@RequestBody Users users){

        return userService.saveUsingEmail(users);
    }



   /* @GetMapping(path = "/find", params = "email")
    public ResponseEntity<?> findUsingEmail(@RequestParam String email) {
        var result = userService.findUserByEmail(email);
        return ResponseEntity.of(result);
    }
    */

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

    @GetMapping(path = "/getMyDogs", params = {"username"}) //username for owner that is
    public ResponseEntity<?> getMyDogs(@RequestParam String username){
        return ResponseEntity.ok(dogService.getDogs(username));
    }

}
