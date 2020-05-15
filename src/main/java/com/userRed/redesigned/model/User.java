package com.userRed.redesigned.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements UserDetails {

//    @JsonIgnore
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
//    @Column(name = "username")
//    private String username;
//    @Column(name = "email")
//    @Email
//    private String email;
//    @JsonIgnore
//    @Column(name = "password")
//    private String password;
//    @Column(name = "date_of_birth")
//    private String date_of_birth;
//    @Column(name = "gender_type")
//    private String gender_type;
//    @Column(name = "description")
//    private String description;
//    @Column(name = "acc_created")
//    private String createdAt;

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "user_dogs",
//			joinColumns = @JoinColumn(name = "user_id"),
//			inverseJoinColumns = @JoinColumn(name = "dog_id"))

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_dogs",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "dog_id", referencedColumnName = "id"))

	private Set<Dog> dogList;
	private String date_of_birth;
	private String gender_type;
	private String description;
	private String createdAt;

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String userId;
	@NotBlank
	@Unique
	private String username;
	@NotBlank
	@Unique
	@Email
	private String email; // FB
	private String phoneNumber; // FB
	@NotBlank
	@JsonIgnore
	private String password;
	private String displayName; // FB
	private String photoUrl; // FB

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;

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

	public User setRoles(Collection<Role> roles) {
		this.roles = new HashSet<Role>(roles);
		return this;
	}

	public void setCreatedAt() {
		LocalDate tim = LocalDate.now();
		this.createdAt = tim.toString();
	}
// var authorities = roles.stream()
//				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
//				.collect(Collectors.toSet());
}