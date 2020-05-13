package com.userRed.redesigned.controller;

import com.userRed.redesigned.model.Dog;
import com.userRed.redesigned.model.Users;
import com.userRed.redesigned.repository.UsersRepository;
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
    UsersRepository usersRepository;

    @Autowired
    private DogService dogService;

    // POST MAPPINGS

    /**CALL: localhost:8080/user/register */

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerUser(@RequestBody Users users) throws Exception {
        return userService.save(users);
    }

    /**CALL: localhost:8080/user/registerWithMail */

    @PostMapping(path= "/registerWithMail")
    public ResponseEntity<?> registerUsingMail(@RequestBody Users users){
        return userService.saveUsingEmail(users);
    }

    /**CALL: localhost:8080/user/dog/register?owner=XXXXX */

    @PostMapping(path = "dog/register", params = {"owner"})
    public ResponseEntity<?> register(@RequestBody Dog dog, @RequestParam String owner) {

        return dogService.saveDog(dog, owner);
    }

    // PUT MAPPINGS

    /**CALL: localhost:8080/user/update?username=XXXX&&description=XXXXX */

    @PutMapping(path = "update", params = {"username", "description"})
    public ResponseEntity<?> updateDescription(@RequestParam("username") String username,@RequestParam("description") String description) {

        return ResponseEntity.ok(userService.updateUserDescription(username, description));

    }

    /**CALL: localhost:8080/user/update?username=XXXX&&password=XXXXX */

    @PutMapping(path = "update", params = {"username", "password"})
    public ResponseEntity<?> updatePassword(@RequestParam("username") String username,@RequestParam("password") String password) {

        return ResponseEntity.ok(userService.updatePassword(username, password));

    }

    /**CALL: localhost:8080/user/update?username=XXXX&&dateofbirth=XXXXX */

    @PutMapping(path = "update", params = {"username", "date_of_birth"})
    public ResponseEntity<?> updateDateOfBirth(@RequestParam("username") String username,@RequestParam("date_of_birth") String date_of_birth) {

        return ResponseEntity.ok(userService.updateDateOfBirth(username, date_of_birth));

    }

    /**CALL: localhost:8080/user/update?username=XXXX&&email=XXXXX */

    @PutMapping(path = "update", params = {"username", "email"})
    public ResponseEntity<?> updateEmail(@RequestParam("username") String username,@RequestParam("email") String email) {

        return ResponseEntity.ok(userService.updateEmail(username, email));

    }

    // GET MAPPINGS

    /**CALL: localhost:8080/user/getMyDogs?username=XXXX */

    @GetMapping(path = "/getMyDogs", params = {"username"}) //username for owner that is
    public ResponseEntity<?> getMyDogs(@RequestParam String username){
        return ResponseEntity.ok(dogService.getDogs(username));
    }

    /**CALL: localhost:8080/user/find?username=XXXX */

    @GetMapping(path = "/find", params = "username")
    public ResponseEntity<?> findByName(@RequestParam String username) {

        var result = userService.findByName(username);
        return ResponseEntity.of(result);

    }

    /**CALL: localhost:8080/user/find?email=XXXX */

    @GetMapping(path = "/find", params = "email")
    public ResponseEntity<?> findUsingEmail(@RequestParam String email) {
        var result = userService.findUserByEmail(email);
        return ResponseEntity.of(result);
    }
}
