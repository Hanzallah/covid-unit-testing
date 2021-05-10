package com.covidunit.covidunitapi.Symptoms;

import com.covidunit.covidunitapi.User.UserModel;
import com.covidunit.covidunitapi.User.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/api/v1/symptoms")
@RestController
public class SymptomController {
    @Autowired
    SymptomRepo symptomRepo;
    @Autowired
    UserRepo userRepo;

    private ModelMapper mapper = new ModelMapper();

    @PostMapping("/create/{id}")
    public ResponseEntity<?> createSymptoms(@PathVariable(value="id") long id, @RequestBody SymptomModel requestNewSymptoms){
        try{
            UserModel user = userRepo.findById(id).get();

            SymptomModel symptomModel = mapper.map(requestNewSymptoms, SymptomModel.class);

            List<SymptomModel> allSymptoms = symptomRepo.findAllByCreator(user);

//            for (SymptomModel curSymptom: allSymptoms){
//                if (curSymptom.didUploadToday()){
//                    Map<String,String> map = new HashMap<>();
//                    map.put("message", "Symptom for today already exists!");
//                    map.put("code", "0");
//                    return new ResponseEntity<>(map, HttpStatus.OK);
//                }
//            }

            int symptomCount = 0;
            if (symptomModel.getFever() > 100) { symptomCount++; }
            if (symptomModel.getDifficultyBreathing()) {symptomCount++; }
            if (symptomModel.getCough()) {symptomCount++; }
            if (symptomModel.getTiredness()) { symptomCount++; }

            symptomModel.setNumSymptoms(symptomCount);
            user.addSymptoms(symptomModel);
            userRepo.save(user);

            Map<String,Object> map = new HashMap<>();
            map.put("code", "1");
            map.put("message", "Symptom snapshot created successfully!");
            map.put("payload", user.toMap());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String,String> map = new HashMap<>();
            map.put("code", "0");
            map.put("message", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSymptoms(@PathVariable(value="id") long id){
        try{
            UserModel user = userRepo.findById(id).get();
            List<SymptomModel> snapshot = symptomRepo.findAllByCreator(user);
            Map<String,Object> map = new HashMap<>();
            map.put("code", "1");
            map.put("message", "Symptoms snapshot retrieved!");
            map.put("payload", snapshot);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String,String> map = new HashMap<>();
            map.put("code", "0");
            map.put("message", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteSymptoms(){
        try {
            symptomRepo.deleteAll();
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
}
