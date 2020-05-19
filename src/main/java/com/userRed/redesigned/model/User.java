package com.userRed.redesigned.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.userRed.redesigned.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements UserDetails {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String userId;
	@NotBlank
	private String username;
	@NotBlank
	@Email
	private String email; // FB
	@PastOrPresent
	@NonNull
	private LocalDate dateOfBirth;
	@PastOrPresent
	private LocalDate createdAt;
	@NonNull
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String description;
	private String phoneNumber; // FB
//	@NotBlank
	@JsonIgnore
	private String password;
	private String displayName; // FB
	private String photoUrl; // FB
	private String bucket;

	@OneToMany(fetch = FetchType.EAGER) // , cascade = CascadeType.ALL)
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE,
			org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST, })
	@JoinTable(name = "users_dogs",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "dog_id", referencedColumnName = "dog_id"))
	@JsonManagedReference
	private Set<Dog> dogs;

	// extends userdetails
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;

//	@OneToMany(orphanRemoval = true,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@ManyToMany(fetch = FetchType.EAGER) // , cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;
	// Is populated from roles during loading.
	@Transient
	private Collection<SimpleGrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public User setRoles(Role... roles) {
		this.roles = new HashSet<Role>();
		this.roles.addAll(Arrays.asList(roles));
		return this;
	}

	public void setCreatedAt() {
		createdAt = LocalDate.now(); // .toString();
	}

	public boolean hasDog(@Valid @NonNull Dog dog) {
		return hasDog(dog.getName());
	}

	public boolean hasDog(@Valid @NotBlank String name) {
		return getDog(name) != null;
	}

	public Dog getDog(@Valid @NotBlank String name) {
		return dogs.stream()
				.filter(dog -> dog.getName()
						.equals(name))
				.findFirst()
				.orElse(null);
	}

	public boolean removeDog(@NotBlank String name) {
		return dogs.remove(getDog(name));
	}

	// var authorities = roles.stream()
//				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
//				.collect(Collectors.toSet());
}