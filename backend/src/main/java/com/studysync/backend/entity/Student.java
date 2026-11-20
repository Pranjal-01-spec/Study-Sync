package com.studysync.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Column(name = "year_level")
    private String year;
    private String strongSubjects; // Comma separated
    private String weakSubjects;   // Comma separated
    private Integer karmaPoints;

    public Student() {}

    public Student(Long id, String name, String email, String year, String strongSubjects, String weakSubjects, Integer karmaPoints) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.year = year;
        this.strongSubjects = strongSubjects;
        this.weakSubjects = weakSubjects;
        this.karmaPoints = karmaPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStrongSubjects() {
        return strongSubjects;
    }

    public void setStrongSubjects(String strongSubjects) {
        this.strongSubjects = strongSubjects;
    }

    public String getWeakSubjects() {
        return weakSubjects;
    }

    public void setWeakSubjects(String weakSubjects) {
        this.weakSubjects = weakSubjects;
    }

    public Integer getKarmaPoints() {
        return karmaPoints;
    }

    public void setKarmaPoints(Integer karmaPoints) {
        this.karmaPoints = karmaPoints;
    }
}
