package com.userRed.redesigned.firebase;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.extern.java.Log;

@Log
@Configuration
public class FireBaseConfig {

	private InputStream inputStream;
	private FirebaseOptions fireBaseOptions;

	@PostConstruct
	public void fireBaseInitialization() throws Exception {
		if (isFireBaseRunning()) {
			removeRunningFireBase();
		}
		getFireBaseServiceAccountKey();
		createFireBaseOptions();
		initializeFireBase();
	}

	private boolean isFireBaseRunning() {
		return !FirebaseApp.getApps()
				.isEmpty();
	}

	private void removeRunningFireBase() {
		FirebaseApp.getInstance()
				.getApps()
				.forEach(fireBaseApp -> {
					fireBaseApp.delete();
				});
		log.info("Removed all currently running Firebase applications.");
	}

	private void initializeFireBase() throws IOException {
		FirebaseApp.initializeApp(fireBaseOptions);
		log.info("Firebase application " + FirebaseApp.getInstance()
				.getName() + " initialized.");
	}

	private void createFireBaseOptions() throws IOException {
		fireBaseOptions = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(inputStream))
				.setDatabaseUrl("https://pvtdogpark.firebaseio.com")
				.build();
		log.info("Created Firebase options.");
	}

	private void getFireBaseServiceAccountKey() throws IOException {
		inputStream = getClass().getResourceAsStream("/serviceAccountKey.json");
		if (inputStream == null) {
			throw new IOException("Firebase service account resource not found.");
		}
	}

}
