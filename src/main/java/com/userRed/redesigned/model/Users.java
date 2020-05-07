package com.userRed.redesigned.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "dateOfBirth")
    private String dateOfBirth;
    @Column(name = "genderType")
    private String genderType;
    @Column(name = "Acc_Created")
    private String createdAt;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_dogs", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "dog_id"))
    private Set<Dog> DogList;

    public Users() {
    }

    public Users(Users users) {
        this.username = users.getUsername();
        this.email = users.getEmail();
        this.name = users.getName();
        this.password = users.getPassword();
        this.dateOfBirth = users.getDateOfBirth();
        this.genderType = users.getGenderType();
        this.createdAt = getCreatedAt();
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGenderType() {
        return genderType;
    }

    public Set<Dog> getDogList() {
        return DogList;
    }

    public void addToDogList(Dog dog) {
        this.DogList.add(dog);
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        LocalDate tim = LocalDate.now();
        this.createdAt = tim.toString();

    }

}

