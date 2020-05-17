package com.userRed.redesigned.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userRed.redesigned.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "dog_id")
    private Long id;
    @NotBlank
    private String name;
    private String breed;
    @PastOrPresent
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String description;
	@ManyToOne(fetch = FetchType.EAGER) //, cascade=CascadeType.ALL)
	@JoinTable(name = "users_dogs",
			joinColumns = @JoinColumn(name = "dog_id", referencedColumnName = "dog_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	@JsonBackReference
    private User owner;
}
