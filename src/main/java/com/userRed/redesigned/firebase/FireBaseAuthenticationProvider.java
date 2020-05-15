package com.userRed.redesigned.firebase;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.userRed.redesigned.service.UserService;

import lombok.val;
import lombok.extern.java.Log;

@Log
public class FireBaseAuthenticationProvider implements AuthenticationProvider {

	private final UserService userDetailsService;

	public FireBaseAuthenticationProvider(UserService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("Authenticating...");

		val fireBaseAuthenticationToken = (FireBaseAuthenticationToken) authentication;

		try {
			// debug only, normally uid is fetched from firebase as below.
			String uid = "b4OwvLmxWYNLxR3p3dzt9df10DL2";
//			String uid = verifyIdToken(fireBaseAuthenticationToken.getIdToken());

			UserRecord userRecord = FirebaseAuth.getInstance()
					.getUser(uid);

			// debug only
			displayUserRecord(userRecord);

			val user = userDetailsService.loadUserByEmail(userRecord.getEmail());
			for (GrantedAuthority auth : user.getAuthorities()) {
				System.out.println(auth.toString());
			}

			log.info("...authenticated!");
			return new FireBaseAuthenticationToken(user.getUsername(), null, user, user.getAuthorities(), true);

		} catch (FirebaseAuthException e) {
			log.warning("...firebase failed to authenticate!");
			log.warning("Error code= " + e.getErrorCode() + ", Message= " + e.getMessage()); // throw new
																							 // SecurityException(e.getMessage());
		} catch (UsernameNotFoundException e) {
			log.warning("...failed to find user");
			log.warning(e.getMessage()); // "Username not found.");
			throw new BadCredentialsException(e.getMessage());
		}
		log.warning("...failed to authenticate!");
		throw new BadCredentialsException("Authentication failed.");

//		return null;		// osäker på om authentication är trusted eller inte 
		// men då appen bara har firebase auth så ska den kasta ett exception om auth
		// inte går igenom.
	}

	private String verifyIdToken(String idToken) throws FirebaseAuthException {
		FirebaseToken fireBaseToken = FirebaseAuth.getInstance()
				.verifyIdToken(idToken, true);					// true = checkRevoked
		return fireBaseToken.getUid();
	}

	// for debug only
	private void displayUserRecord(UserRecord userRecord) {
		System.out.println("CustomClaims= " + userRecord.getCustomClaims());
		System.out.println("DisplayName= " + userRecord.getDisplayName());
		System.out.println("Email= " + userRecord.getEmail());
		System.out.println("PhoneNumber= " + userRecord.getPhoneNumber());
		System.out.println("PhotoUrl= " + userRecord.getPhotoUrl());
		System.out.println("ProviderId= " + userRecord.getProviderId());
		System.out.println("TokensValidAfterTimestamp= " + userRecord.getTokensValidAfterTimestamp());
		System.out.println("Uid= " + userRecord.getUid());
		System.out.println("ProviderData= " + userRecord.getProviderData());
		System.out.println("UserMetaData= " + userRecord.getUserMetadata());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (FireBaseAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
