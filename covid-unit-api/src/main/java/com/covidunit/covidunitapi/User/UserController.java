package com.covidunit.covidunitapi.User;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class UserController {
    @Autowired
    UserRepo userRepo;

    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    @PostMapping("/api/v1/user/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel requestNewUser){
        List<UserModel> users = userRepo.findAll();

        if (requestNewUser.getEmail() == null || requestNewUser.getPassword() == null || requestNewUser.getName() == null){
            Map<String,String> map = new HashMap<>();
            map.put("message", "Fields cannot be null!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        if (requestNewUser.getGender() != null && requestNewUser.getGender().equals("F")
                && requestNewUser.getGender().equals("M")){
            Map<String,String> map = new HashMap<>();
            map.put("message", "Incorrect gender format!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        for (UserModel user: users){
            if (user.equals(requestNewUser)){
                Map<String,String> map = new HashMap<>();
                map.put("message", "User already exists!");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }

        requestNewUser.setPassword(hashPassword(requestNewUser.getPassword()));
        userRepo.save(requestNewUser);

        Map<String,String> map = new HashMap<>();
        map.put("message", "User saved successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/api/v1/user/login")
    public ResponseEntity<?> userLogin(@RequestBody UserModel requestUser){
        if (requestUser.getEmail() == null || requestUser.getPassword() == null || requestUser.getEmail() == null) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "Fields cannot be null!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        List<UserModel> users = userRepo.findAll();

        for (UserModel other: users){
            if (other.equals(requestUser)){
                if (BCrypt.checkpw(requestUser.getPassword(), other.getPassword())){
                    other.setLoggedIn(true);
                    userRepo.save(other);

                    Map<String,String> map = new HashMap<>();
                    map.put("message", "User logged in successfully");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
                else{
                    Map<String,String> map = new HashMap<>();
                    map.put("message", "Incorrect Password!");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
            }
        }

        Map<String,String> map = new HashMap<>();
        map.put("message", "User does not exist!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/api/v1/user/logout")
    public ResponseEntity<?> logUserOut(@RequestBody UserModel requestUser) {
        if (requestUser.getEmail() == null || requestUser.getPassword() == null || requestUser.getEmail() == null){
            Map<String,String> map = new HashMap<>();
            map.put("message", "Fields cannot be null!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        List<UserModel> users = userRepo.findAll();

        for (UserModel other : users) {
            if (other.equals(requestUser)) {
                other.setLoggedIn(false);
                userRepo.save(other);

                Map<String,String> map = new HashMap<>();
                map.put("message", "User logged out successfully");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }

        Map<String,String> map = new HashMap<>();
        map.put("message", "User does not exist!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @PostMapping("/api/v1/user/reset/password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> requestUserPass) {
        try {
            UserModel user = userRepo.findByEmail(requestUserPass.get("email"));
            user.setPassword(hashPassword(requestUserPass.get("password")));
            userRepo.save(user);

            Map<String,String> map = new HashMap<>();
            map.put("message", "Password updated successfully");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> map = new HashMap<>();
            map.put("message", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @DeleteMapping("/api/v1/users/all")
    public ResponseEntity<?> deleteUsers() {
        try {
            userRepo.deleteAll();
            Map<String, String> map = new HashMap<>();
            map.put("message", "All deleted!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String,String> map = new HashMap<>();
            map.put("message", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping("/api/v1/users/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            Map<String,List<UserModel>> map = new HashMap<>();
            map.put("users", userRepo.findAll());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String,String> map = new HashMap<>();
            map.put("message", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping("/api/v1/user/id")
    public ResponseEntity<?>  getUserID(@RequestBody Map<String, String> requestUserEmail) {
        try {
            UserModel user = userRepo.findByEmail(requestUserEmail.get("email"));
            Map<String,Long> map = new HashMap<>();
            map.put("id", user.getId());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> map = new HashMap<>();
            map.put("message", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
