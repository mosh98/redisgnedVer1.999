package com.userRed.redesigned.cloudstorage;

import java.io.IOException;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userRed.redesigned.firebase.FireBaseAuthenticationToken;
import com.userRed.redesigned.model.User;

import lombok.val;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(path = "/cloud")
public class CloudStorageController {

	@Autowired
	private CloudStorageService cloudStorageService;

	@GetMapping(path = "/images")
	public List<String> getBucketFileList(FireBaseAuthenticationToken authentication) {
		return cloudStorageService.getBucketFileList((User) authentication.getPrincipal());
	}

	/**
	 * Principal object should always be an instance of User so maybe add getUser
	 * method to FireBaseAuthenticationToken instead.
	 * 
	 * @param authentication
	 * @param filename
	 * @return
	 */
	@GetMapping(path = "/images/{filename}")
	public ResponseEntity<?> downloadImage(	FireBaseAuthenticationToken authentication,
											@NotBlank @PathVariable String filename) {
		var principal = authentication.getPrincipal();
		if (principal instanceof User) {
			User user = (User) principal;
			var signedUrl = cloudStorageService.getSignedDownloadUrl(user, filename);
			return ResponseEntity.ok(signedUrl);
		}
		throw new IllegalStateException(
				"Principal field in authentication object is not a valid instance of User class.");
	}

	@PutMapping(path = "/images/{filename}")
	public ResponseEntity<?> uploadImage(	FireBaseAuthenticationToken authentication,
											@NotBlank @PathVariable String filename) {
		var principal = authentication.getPrincipal();
		if (principal instanceof User) {
			User user = (User) principal;
			var signedUrl = cloudStorageService.getSignedImageUploadUrl(user, filename);
			return ResponseEntity.ok(signedUrl);
		}
		throw new IllegalStateException(
				"Principal field in authentication object is not a valid instance of User class.");
	}

	@PreAuthorize("#username == authentication.principal.username")
	@PutMapping(path = "/{username}")
	public String createBucket(	FireBaseAuthenticationToken authentication,
								@PathVariable String username)
			throws IOException {
		val bucket = cloudStorageService.createRandomBucket();
		cloudStorageService.displayAllBuckets();
		return "Created bucket " + bucket.getName();
	}

	@PreAuthorize("#username == authentication.principal.username")
	@DeleteMapping(path = "/{username}/{bucketname}")
	public String deleteBucket(	FireBaseAuthenticationToken authentication,
								@PathVariable String username,
								@PathVariable String bucketname) {
		cloudStorageService.deleteBucket(bucketname);
		return "Deleted bucket " + bucketname;
	}

}
