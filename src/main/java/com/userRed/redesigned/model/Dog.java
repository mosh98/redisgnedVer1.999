package com.userRed.redesigned.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.userRed.redesigned.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dog")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_id")
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String breed;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String description;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id", nullable = false)
    
	@ManyToOne(fetch = FetchType.EAGER) //, cascade=CascadeType.ALL)
//	@JoinTable(name = "users_dogs",
//			joinColumns = @JoinColumn(name = "dog_id", referencedColumnName = "dog_id"),
//			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private User owner;
}
