package com.userRed.redesigned.config;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.extern.java.Log;

@Log
@Configuration
public class FireBaseConfig {

	private final static String FIRE_BASE = "https://pvtdogpark.firebaseio.com";
	private final static String CLOUD_STORAGE = "pvtdogpark.appspot.com";
	private final static String PROJECT_ID = "pvtdogpark";

//	private InputStream inputStream;
	private GoogleCredentials googleCredentials;
	private FirebaseOptions fireBaseOptions;
	private Storage cloudStorage;

	@Bean
	public Storage cloudStorage() {
		return cloudStorage;
	}

	@PostConstruct
	public void fireBaseInitialization() throws Exception {
		if (isFireBaseRunning()) {
			removeRunningFireBase();
		}
		getGoogleCredentials();
		createFireBaseOptions();
		initializeFireBase();
		initializeCloudStorage();
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

	private void getGoogleCredentials() throws IOException {
		googleCredentials = GoogleCredentials.fromStream(getFireBaseServiceAccountKey());
	}
	
	private InputStream getFireBaseServiceAccountKey() throws IOException {
		InputStream inputStream = getClass().getResourceAsStream("/serviceAccountKey.json");
		if (inputStream == null) {
			throw new IOException("Firebase service account resource not found.");
		}
		return inputStream;
	}

	private void createFireBaseOptions() throws IOException {
		fireBaseOptions = new FirebaseOptions.Builder().setCredentials(googleCredentials)
				.setDatabaseUrl(FIRE_BASE)
				.setStorageBucket(CLOUD_STORAGE)
				.build();
		log.info("Created Firebase options.");
	}

	private void initializeFireBase() throws IOException {
		FirebaseApp.initializeApp(fireBaseOptions);
		log.info("Initialized Firebase application " + FirebaseApp.getInstance()
				.getName());
	}

	private void initializeCloudStorage() {
		cloudStorage = StorageOptions.newBuilder()
				.setCredentials(googleCredentials)
				.setProjectId(PROJECT_ID)
				.build()
				.getService();
		log.info("Initialized Cloud Storage for project " + PROJECT_ID);
	}
}
