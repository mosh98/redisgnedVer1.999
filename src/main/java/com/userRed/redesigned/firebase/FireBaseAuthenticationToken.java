package com.userRed.redesigned.firebase;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class FireBaseAuthenticationToken extends AbstractAuthenticationToken {

	private final Object details;											// username
	private final Object credentials;										// password
	private final Object principal;										// userdetails
	private final Collection<? extends GrantedAuthority> authorities;		// authorities/roles
	private final boolean isAuthenticated;						// authenticated

	public FireBaseAuthenticationToken(String username,
			String password,
			Object userDetails,
			Collection<? extends GrantedAuthority> grantedAuthorities,
			boolean isAuthenticated) {
		super(null);
		details = username;
		credentials = password;
		principal = userDetails;
		authorities = grantedAuthorities;
		this.isAuthenticated = isAuthenticated;
	}

	public FireBaseAuthenticationToken(String username, String password) {
		this(username, password, null, null, false);
	}

	public FireBaseAuthenticationToken(String idToken) {
		this(idToken, null, null, null, false);
	}

	@Override
	public String getName() {
		return (String) details;
	}

	public String getPassword() {
		return (String) credentials;
	}

	public String getIdToken() {
		return (String) details;
	}

	@SuppressWarnings("unchecked")	// safe to cast, SimpleGrantedAuthortiy -> GrantedAuthroity.
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return (Collection<GrantedAuthority>) authorities;
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getDetails() {
		return details;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		throw new SecurityException("Illegal manipulation of isAuthenticated field, token not to be trusted");
	}

}
