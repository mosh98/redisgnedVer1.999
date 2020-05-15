package com.userRed.redesigned.service;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.userRed.redesigned.model.User;

import lombok.extern.java.Log;

@Log
@Service
public class FireBaseService {
	
	public boolean existsByEmail(@Valid @NotBlank @Email String email) {
		try {
			FirebaseAuth.getInstance()
					.getUserByEmail(email);
		} catch (FirebaseAuthException e) {
			log.info("User don't exists by email: " + email);
			return false;
		}
		log.info("User exists by email: " + email);
		return true;
	}
}
