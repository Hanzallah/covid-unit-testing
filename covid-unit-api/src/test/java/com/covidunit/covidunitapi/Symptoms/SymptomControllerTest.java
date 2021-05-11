package com.covidunit.covidunitapi.Symptoms;

import com.covidunit.covidunitapi.User.UserController;
import com.covidunit.covidunitapi.User.UserModel;
import com.covidunit.covidunitapi.User.UserRepo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SymptomControllerTest {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserController userCont;
    @Autowired
    SymptomController symController;

    @Test
    @Order(1)
    void createSymptoms() {
        userRepo.deleteAll();
        UserModel user = new UserModel("esra","esra@gmail.com", "123456789");
        user.setAge(29);
        user.setGender("F");
        user.setCountry("Canada");
        user.setCity("Montreal");
        userCont.registerUser(user);
        Map<String, String> map = new HashMap<>();
        map.put("email","esra@gmail.com" );
        map.put("password","123456789");
        userCont.userLogin(map);

        SymptomModel symModel = new SymptomModel();
        symModel.setCough(true);
        symModel.setDifficultyBreathing(true);
        symModel.setFever(37);
        LocalDateTime lt = LocalDateTime.now();
        symModel.setCreatedAt(lt);
        symModel.getCreatedAt();
        symModel.didUploadToday();
        symModel.getCreator();

        ResponseEntity<?> response = symController.createSymptoms(1, symModel);
        System.out.println("Unsuccesful symptom creation with null fields:");
        Map<String,Object> resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("0"));

        symModel.setTiredness(true);

        response = symController.createSymptoms(1, symModel);
        System.out.println("Succesful symptom creation:");
        resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("1"));

        response = symController.createSymptoms(2, symModel);
        System.out.println("Unsuccesful symptom creation with non-existent user:");
        resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("0"));

    }

    @Test
    @Order(2)
    void getSymptoms() {
        ResponseEntity<?> response = symController.getSymptoms(1);
        System.out.println("Succesful get symptoms:");
        Map<String,Object> resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("1"));

        response = symController.getSymptoms(2);
        System.out.println("Unsuccesful get symptoms with non-existent user:");
        resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("0"));
    }

    @Test
    @Order(3)
    void deleteSymptoms() {
        ResponseEntity<?> response = symController.deleteSymptoms();
        System.out.println("Succesful delete symptoms:");
        Map<String,Object> resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("1"));
    }
}