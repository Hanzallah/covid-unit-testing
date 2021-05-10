package com.covidunit.covidunitapi.User;
import com.covidunit.covidunitapi.Symptoms.SymptomModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

@Entity
@Table(name = "users")
public class UserModel {
    private @Id @GeneratedValue(strategy= GenerationType.IDENTITY) long id;
    private @Column(name = "name", nullable=false, length=50) String name;
    private @Column(name = "age") Integer age;
    private @Column(name = "gender", length=1) String gender;
    private @Column(name = "city", length=20) String city;
    private @Column(name = "country", length=20) String country;
    private @Column(name = "email", nullable=false, length=50,  unique = true) String email;
    private @Column(name = "password", nullable=false) String password;
    private @Column(name = "loggedIn") boolean loggedIn;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SymptomModel> symptoms = new ArrayList<SymptomModel>();

    public UserModel(){}
    public UserModel(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.loggedIn = false;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserModel)) return false;
        UserModel user = (UserModel) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, loggedIn);
    }

    @Override
    public String toString() {
        List<String> symList = new ArrayList<>();
        String symString = "";
        for (SymptomModel symModel: symptoms){
            symString += symModel.toString();
            symList.add(symString);
        }
        return "{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", password='" + password +
                '\'' + ", age='" +  (age != null ? age.toString() : "") + '\'' + ", gender='" + (gender != null ? gender : "") +
                '\'' + ", city='" +  (city != null ? city : "") +
                '\'' + ", country='" +  (country != null ? country : "") + '\'' +
                ", loggedIn=" + loggedIn + '\'' +
                ", symptoms=" + symList.toString() + '}';
    }

    public List<SymptomModel> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<SymptomModel> symptoms) {
        this.symptoms = symptoms;
    }

    public void addSymptoms(SymptomModel newSymptoms) {
        if (newSymptoms != null) {
            newSymptoms.setCreator(this);
            symptoms.add(newSymptoms);
        }
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
