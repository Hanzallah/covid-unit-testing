package com.covidunit.covidunitapi.Symptoms;
import javax.persistence.*;
import java.util.Objects;
import java.time.LocalDateTime;

import com.covidunit.covidunitapi.User.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "symptoms")
public class SymptomModel {
    private @Id @GeneratedValue(strategy= GenerationType.IDENTITY) long id;

    @Column(name = "fever", nullable = false)
    private Integer fever;
    @Column(name = "cough", nullable = false)
    private Boolean cough;
    @Column(name = "tiredness", nullable = false)
    private Boolean tiredness;
    @Column(name = "difficultyBreathing", nullable = false)
    private Boolean difficultyBreathing;
    @Column(name = "numSymptoms", nullable = false)
    private Integer numSymptoms;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    @JsonIgnore
    @JoinColumn(name = "creator_id")
    private UserModel creator;

    public SymptomModel(){}

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Symptoms{" +
                "id=" + id +
                ", fever=" + fever +
                ", cough=" + cough +
                ", tiredness=" + tiredness +
                ", difficultyBreathing=" + difficultyBreathing +
                '}';
    }

    public Integer getFever() {
        return fever;
    }

    public void setFever(Integer fever) {
        this.fever = fever;
    }

    public Boolean getCough() {
        return cough;
    }

    public void setCough(Boolean cough) {
        this.cough = cough;
    }

    public Boolean getTiredness() {
        return tiredness;
    }

    public void setTiredness(Boolean tiredness) {
        this.tiredness = tiredness;
    }

    public Boolean getDifficultyBreathing() {
        return difficultyBreathing;
    }

    public void setDifficultyBreathing(Boolean difficultyBreathing) {
        this.difficultyBreathing = difficultyBreathing;
    }

    public Integer getNumSymptoms() {
        return numSymptoms;
    }

    public void setNumSymptoms(Integer numSymptoms) {
        this.numSymptoms = numSymptoms;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public boolean didUploadToday() {
        LocalDateTime now = LocalDateTime.now();

        return Objects.equals(createdAt.getMonth(), now.getMonth())
                && Objects.equals(createdAt.getYear(), now.getYear())
                && Objects.equals(createdAt.getDayOfMonth(), now.getDayOfMonth());
    }
}
