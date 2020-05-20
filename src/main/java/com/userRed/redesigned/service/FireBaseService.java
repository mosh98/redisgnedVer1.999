package com.userRed.redesigned.service;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import lombok.extern.java.Log;

@Log
@Service
public class FireBaseService {

	public boolean existsByEmail(@Valid @NotBlank @Email String email) {
		try {
			FirebaseAuth.getInstance()
					.getUserByEmail(email);
		} catch (FirebaseAuthException e) {
			log.info(String.format("User with email %s not found in Firebase", email));
			return false;
		}
		log.info(String.format("No user with email %s found in Firebase", email));
		return true;
	}

	public String getUserIdByEmail(@NotBlank String email) {
		try {
			UserRecord userRecord = FirebaseAuth.getInstance()
					.getUserByEmail(email);
			return userRecord.getUid();
		} catch (FirebaseAuthException e) {
			throw new SecurityException("User with email " + email + " not found in Firebase");
		}
	}
}
