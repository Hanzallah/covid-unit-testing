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

@RestController
public class SymptomController {
    @Autowired
    SymptomRepo symptomRepo;
    @Autowired
    UserRepo userRepo;

    private ModelMapper mapper = new ModelMapper();

    @PostMapping("/api/v1/symptoms/create/{id}")
    public ResponseEntity<?> createSymptoms(@PathVariable(value="id") long id, @RequestBody SymptomModel newSymptoms){
        try{
            UserModel user = userRepo.findById(id).get();

            SymptomModel symptomModel = mapper.map(newSymptoms, SymptomModel.class);

            List<SymptomModel> allSymptoms = symptomRepo.findAllByCreator(user);

            for (SymptomModel curSymptom: allSymptoms){
                if (curSymptom.didUploadToday()){
                    Map<String,String> map = new HashMap<>();
                    map.put("error", "Symptom for today already exists!");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
            }

            int symptomCount = 0;
            if (symptomModel.getFever() > 100) { symptomCount++; }
            if (symptomModel.getDifficultBreathing()) {symptomCount++; }
            if (symptomModel.getCough()) {symptomCount++; }
            if (symptomModel.getTiredness()) { symptomCount++; }

            symptomModel.setNumSymptoms(symptomCount);
            user.addSymptoms(symptomModel);
            userRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e){
            Map<String,String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping("/api/v1/symptoms/{id}")
    public ResponseEntity<?> getSymptoms(@PathVariable(value="id") long id){
        try{
            UserModel user = userRepo.findById(id).get();
            List<SymptomModel> entityTracks = symptomRepo.findAllByCreator(user);
            return new ResponseEntity<>(entityTracks, HttpStatus.OK);
        } catch (Exception e){
            Map<String,String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @DeleteMapping("/api/v1/symptoms")
    public ResponseEntity<?> deleteSymptoms(){
        try {
            symptomRepo.deleteAll();
            Map<String, String> map = new HashMap<>();
            map.put("message", "All deleted!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String,String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
