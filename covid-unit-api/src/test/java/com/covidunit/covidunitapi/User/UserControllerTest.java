package com.covidunit.covidunitapi.User;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserController userCont;

    @Test
    void registerUser() {
        userRepo.deleteAll();

        //unsuccessful register with empty data fields
        UserModel userEmpty = new UserModel();
        ResponseEntity<?> response = userCont.registerUser(userEmpty);
        System.out.println("Unsuccesful register with empty data fields:");
        Map<String,Object> resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("0"));

        UserModel user = new UserModel();
        user.setName("esra");
        Assert.isTrue(user.getName().equals("esra"));
        user.setEmail("esra@gmail.com");
        Assert.isTrue(user.getEmail().equals("esra@gmail.com"));
        user.setPassword("123456789");
        Assert.isTrue(user.getPassword().equals("123456789"));

        user.setAge(29);
        user.setGender("F");
        user.setCountry("Canada");
        user.setCity("Montreal");
        user.toString();

        //successful register
        response = userCont.registerUser(user);
        System.out.println("Succesful register:");
        resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("1"));
    }

    @Test
    void userLogin() {
        userRepo.deleteAll();
        UserModel user = new UserModel("esra","esra@gmail.com", "123456789");
        user.setAge(29);
        user.setGender("F");
        user.setCountry("Canada");
        user.setCity("Montreal");
        userCont.registerUser(user);

        Map<String, String> map = new HashMap<>();

        //successful login
        map.put("email","esra@gmail.com" );
        map.put("password","123456789");
        ResponseEntity<?> response = userCont.userLogin(map);
        System.out.println("Succesful login:");
        Map<String,Object> resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("1"));
        user.isLoggedIn();
        user.hashCode();
        user.getSymptoms();
        user.getAge();
        user.getCity();
        user.getCountry();

        //unsuccessful login with wrong password
        map.put("email","esra@gmail.com" );
        map.put("password","1111111");
        response = userCont.userLogin(map);
        System.out.println("Unsuccessful login with wrong password:");
        resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("0"));

        //unsuccessful login with empty credential
        map.put("email","" );
        map.put("password","");
        response = userCont.userLogin(map);
        System.out.println("Unsuccessful login with empty credential:");
        resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("0"));
    }

    @Test
    void logUserOut() {
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
        ResponseEntity<?> response = userCont.logUserOut(map);
        System.out.println("Succesful logout:");
        Map<String,Object> resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("1"));

        Map<String, String> mapWrong = new HashMap<>();
        mapWrong.put("email","deniz@gmail.com" );
        mapWrong.put("password","123456789");
        userCont.userLogin(map);
        response = userCont.logUserOut(mapWrong);
        System.out.println("Unsuccesful logout with wrong credentials:");
        resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("0"));

        mapWrong.put("email","" );
        mapWrong.put("password","");
        userCont.userLogin(map);
        response = userCont.logUserOut(mapWrong);
        System.out.println("Unsuccesful logout with empty credentials:");
        resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("0"));
    }

    @Test
    void resetPassword() {
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

        map.put("password","111111111");
        ResponseEntity<?> response = userCont.resetPassword(map);
        System.out.println("Succesful reset password:");
        Map<String,Object> resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("1"));

        map.put("email","deniz@gmail.com" );
        response = userCont.logUserOut(map);
        System.out.println("Unsuccesful logout with wrong credentials:");
        resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("0"));
    }

    @Test
    void deleteUsers() {
        userRepo.deleteAll();
        UserModel user = new UserModel("esra","esra@gmail.com", "123456789");
        user.setAge(29);
        user.setGender("F");
        user.setCountry("Canada");
        user.setCity("Montreal");
        userCont.registerUser(user);

        UserModel user2 = new UserModel("farid","farid@gmail.com", "123456789");
        user2.setAge(29);
        user2.setGender("M");
        user2.setCountry("Azerbaycan");
        user2.setCity("Baku");
        userCont.registerUser(user2);

        ResponseEntity<?> response = userCont.deleteUsers();
        System.out.println("Succesful delete users with 2 users:");
        Map<String,Object> resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("1"));

        response = userCont.deleteUsers();
        System.out.println("Succesful delete users with no users:");
        resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("1"));
    }

    @Test
    void getAllUsers() {
        userRepo.deleteAll();
        UserModel user = new UserModel("esra","esra@gmail.com", "123456789");
        user.setAge(29);
        user.setGender("F");
        user.setCountry("Canada");
        user.setCity("Montreal");
        userCont.registerUser(user);

        UserModel user2 = new UserModel("farid","farid@gmail.com", "123456789");
        user2.setAge(29);
        user2.setGender("M");
        user2.setCountry("Azerbaycan");
        user2.setCity("Baku");
        userCont.registerUser(user2);

        ResponseEntity<?> response = userCont.getAllUsers();
        Map<String,Object> res = (Map<String, Object>) response.getBody();
        System.out.println("Succesful get all users:");
        System.out.println("Response message: " + res.get("message"));
        Assert.isTrue(res.get("code") == "1");
    }

    @Test
    void getUserID() {
        userRepo.deleteAll();
        UserModel user = new UserModel("esra","esra@gmail.com", "123456789");
        user.setAge(29);
        user.setGender("F");
        user.setCountry("Canada");
        user.setCity("Montreal");
        userCont.registerUser(user);

        Map<String, String> map = new HashMap<>();
        map.put("email","esra@gmail.com" );
        ResponseEntity<?> response = userCont.getUserID(map);
        System.out.println("Succesful get user id:");
        Map<String,Object> resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("1"));

        map.put("email","farid@gmail.com" );
        response = userCont.getUserID(map);
        System.out.println("Unsuccesful get user id:");
        resMap = (Map<String, Object>) response.getBody();
        System.out.println("Response message: " + resMap.get("message"));
        Assert.isTrue(resMap.get("code").equals("0"));
    }
}

