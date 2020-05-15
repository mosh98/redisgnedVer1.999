package com.userRed.redesigned.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private String password;
	private String displayName; // FB
	private String photoUrl; // FB

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
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
}

// var authorities = roles.stream()
//				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
//				.collect(Collectors.toSet());
