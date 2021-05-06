package com.covidunit.covidunitapi.Symptoms;

import com.covidunit.covidunitapi.User.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SymptomRepo extends JpaRepository<SymptomModel, Long> {
    List<SymptomModel> findAllByCreator(UserModel entityUser);
}