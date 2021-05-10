package com.covidunit.covidunitapi.User;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

@CrossOrigin
@RequestMapping("/api/v1/user")
@RestController
public class UserController {
    @Autowired
    UserRepo userRepo;

    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel requestNewUser){
        List<UserModel> users = userRepo.findAll();

        if (requestNewUser.getEmail() == null || requestNewUser.getPassword() == null || requestNewUser.getName() == null){
            Map<String,String> map = new HashMap<>();
            map.put("code", "0");
            map.put("message", "Fields cannot be null!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        if (requestNewUser.getGender() != null && requestNewUser.getGender().equals("F")
                && requestNewUser.getGender().equals("M")){
            Map<String,String> map = new HashMap<>();
            map.put("code", "0");
            map.put("message", "Incorrect gender format!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        for (UserModel user: users){
            if (user.equals(requestNewUser)){
                Map<String,String> map = new HashMap<>();
                map.put("code", "0");
                map.put("message", "User already exists!");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }

        requestNewUser.setPassword(hashPassword(requestNewUser.getPassword()));
        userRepo.save(requestNewUser);

        Map<String,Object> map = new HashMap<>();
        map.put("code", "1");
        map.put("message", "User saved successfully");
        map.put("payload", requestNewUser.toMap());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody Map<String, String> requestEmailPass){
        if (requestEmailPass.get("email") == null || requestEmailPass.get("password") == null){
            Map<String, String> map = new HashMap<>();
            map.put("code", "0");
            map.put("message", "Fields cannot be null!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        List<UserModel> users = userRepo.findAll();

        for (UserModel other: users){
            if (other.getEmail().equals(requestEmailPass.get("email"))){
                if (BCrypt.checkpw(requestEmailPass.get("password"), other.getPassword())){
                    other.setLoggedIn(true);
                    userRepo.save(other);
                    Map<String,Object> map = new HashMap<>();
                    map.put("message", "User logged in successfully!");
                    map.put("code", "1");
                    map.put("payload", other.toMap());
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
                else{
                    Map<String,String> map = new HashMap<>();
                    map.put("code", "0");
                    map.put("message", "Incorrect Password!");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
            }
        }

        Map<String,String> map = new HashMap<>();
        map.put("code", "0");
        map.put("message", "User does not exist!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logUserOut(@RequestBody Map<String, String> requestEmailPass) {
        if (requestEmailPass.get("email") == null || requestEmailPass.get("password") == null){
            Map<String,String> map = new HashMap<>();
            map.put("code", "0");
            map.put("message", "Fields cannot be null!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        List<UserModel> users = userRepo.findAll();

        for (UserModel other : users) {
            if (other.getEmail().equals(requestEmailPass.get("email"))) {
                other.setLoggedIn(false);
                userRepo.save(other);

                Map<String,String> map = new HashMap<>();
                map.put("code", "1");
                map.put("message", "User logged out successfully");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }

        Map<String,String> map = new HashMap<>();
        map.put("code", "0");
        map.put("message", "User does not exist!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @PostMapping("/reset/password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> requestEmailPass) {
        try {
            UserModel user = userRepo.findByEmail(requestEmailPass.get("email"));
            user.setPassword(hashPassword(requestEmailPass.get("password")));
            userRepo.save(user);

            Map<String,String> map = new HashMap<>();
            map.put("code", "1");
            map.put("message", "Password updated successfully");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> map = new HashMap<>();
            map.put("code", "0");
            map.put("message", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteUsers() {
        try {
            userRepo.deleteAll();
            Map<String, String> map = new HashMap<>();
            map.put("code", "1");
            map.put("message", "All deleted!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String,String> map = new HashMap<>();
            map.put("code", "0");
            map.put("message", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("payload", userRepo.findAll());
            map.put("message", "All users retrieved!");
            map.put("code", "1");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String,String> map = new HashMap<>();
            map.put("message", e.toString());
            map.put("code", "0");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping("/id")
    public ResponseEntity<?>  getUserID(@RequestBody Map<String, String> requestUserEmail) {
        try {
            UserModel user = userRepo.findByEmail(requestUserEmail.get("email"));
            Map<String,Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("code", "1");
            map.put("message", "Id retrieved!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> map = new HashMap<>();
            map.put("message", e.toString());
            map.put("code", "0");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
