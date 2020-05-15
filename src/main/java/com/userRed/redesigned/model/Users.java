package com.userRed.redesigned.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "user")
public class Users {
    
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    @Email
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "date_of_birth")
    private String date_of_birth;
    @Column(name = "gender_type")
    private String gender_type;
    @Column(name = "description")
    private String description;
    @Column(name = "acc_created")
    private String createdAt;

//....
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_dogs", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "dog_id"))
    private Set<Dog> dogList;

    public Users( ) {
    }

    public Users(Users users) {
        this.username = users.getUsername();
        this.email = users.getEmail();
        this.password = users.getPassword();
        this.date_of_birth = users.getDate_of_birth();
        this.gender_type = users.getGender_type();
        this.createdAt = getCreatedAt();
        this.dogList = getDogList();
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setGender_type(String gender_type) {
        this.gender_type = gender_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getGender_type() {
        return gender_type;
    }

    public Set<Dog> getDogList() {
        return dogList;
    }

    public void addToDogList(Dog dog) {
        this.dogList.add(dog);
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        LocalDate tim = LocalDate.now();
        this.createdAt = tim.toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

