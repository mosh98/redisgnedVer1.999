package com.userRed.redesigned.service;

import java.util.logging.Logger;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

@Service
public class FireBaseService {

	private static final Logger logger = Logger.getLogger("com.backend.service.FireBaseService");

	public boolean existsByEmail(@Valid @NotBlank @Email String email) {
		try {
			FirebaseAuth.getInstance()
					.getUserByEmail(email);
		} catch (FirebaseAuthException e) {
			logger.info("User don't exists by email: " + email);
			return false;
		}
		logger.info("User exists by email: " + email);
		return true;
	}
}
