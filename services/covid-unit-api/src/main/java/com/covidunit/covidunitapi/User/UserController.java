package com.covidunit.covidunitapi.User;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepo userRepo;


    @PostMapping("/api/v1/users/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel newUser){
        List<UserModel> users = userRepo.findAll();

        if (newUser.getEmail() == null || newUser.getPassword() == null || newUser.getEmail() == null){
            Map<String,String> map = new HashMap<>();
            map.put("error", "Fields cannot be null!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        for (UserModel user: users){
            if (user.equals(newUser)){
                Map<String,String> map = new HashMap<>();
                map.put("error", "User already exists!");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }
        userRepo.save(newUser);
        return new ResponseEntity<UserModel>(newUser, HttpStatus.OK);
    }

    @PostMapping("/api/v1/users/login")
    public ResponseEntity<?> userLogin(@RequestBody UserModel user){
        if (user.getEmail() == null || user.getPassword() == null || user.getEmail() == null){
            Map<String,String> map = new HashMap<>();
            map.put("error", "Fields cannot be null!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        List<UserModel> users = userRepo.findAll();

        for (UserModel other: users){
            if (other.equals(user)){
                other.setLoggedIn(true);
                userRepo.save(other);
                return new ResponseEntity<>(other, HttpStatus.OK);
            }
        }
        Map<String,String> map = new HashMap<>();
        map.put("error", "User does not exist!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/api/v1/users/logout")
    public ResponseEntity<?> logUserOut(@RequestBody UserModel user) {
        if (user.getEmail() == null || user.getPassword() == null || user.getEmail() == null){
            Map<String,String> map = new HashMap<>();
            map.put("error", "Fields cannot be null!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        List<UserModel> users = userRepo.findAll();

        for (UserModel other : users) {
            if (other.equals(user)) {
                other.setLoggedIn(false);
                userRepo.save(other);
                return new ResponseEntity<>(other, HttpStatus.OK);
            }
        }

        Map<String,String> map = new HashMap<>();
        map.put("error", "User does not exist!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @PostMapping("/api/v1/users/reset/password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> userPass) {
        try {
            UserModel user = userRepo.findByEmail(userPass.get("email"));
            String val = userPass.get("password");
            user.setPassword(val);
            userRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/api/v1/users/all")
    public ResponseEntity<?> deleteUsers() {
        userRepo.deleteAll();
        Map<String,String> map = new HashMap<>();
        map.put("message", "All deleted!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/api/v1/users/all")
    public List<UserModel> getAllUsers() {
        return userRepo.findAll();
    }
}
