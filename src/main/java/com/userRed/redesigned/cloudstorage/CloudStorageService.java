package com.userRed.redesigned.cloudstorage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Strings;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.HttpMethod;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.SignUrlOption;
import com.google.cloud.storage.StorageClass;
import com.userRed.redesigned.model.User;
import com.userRed.redesigned.service.UserService;

import lombok.NonNull;
import lombok.extern.java.Log;

@Log
@Service
public class CloudStorageService {

	private static final StorageClass STORAGE_CLASS = StorageClass.STANDARD;
	private static final String LOCATION = "eu";
	private static final Integer SIGNED_URL_TTL = 120;
	private static final TimeUnit SIGNED_URL_TTL_UNIT = TimeUnit.SECONDS;
	@Autowired
	private Storage cloudStorage;

	@Autowired
	private UserService userService;

	public CloudStorageService(Storage cloudStorage) {
		this.cloudStorage = cloudStorage;
	}

	public List<String> getBucketFileList(@Valid User user) {
		var fileList = new ArrayList<String>();

		var bucketName = user.getBucket();
		if (Strings.isNullOrEmpty(bucketName)) {
			throw new SecurityException("User missing bucket.");
		}

		Bucket bucket = cloudStorage.get(bucketName);
		Page<Blob> blobs = bucket.list();
		Iterable<Blob> blobIterator = blobs.iterateAll();
		for (Blob blob : blobIterator) {
			fileList.add(blob.getName()
					.toString());
		}
		return fileList;
	}

	public String getSignedDownloadUrl(	@Valid User user,
										@NotBlank String filename) {
		var bucketName = user.getBucket();
		if (Strings.isNullOrEmpty(bucketName)) {
			throw new SecurityException("User is missing bucket.");
		}

		URL signedUrl = cloudStorage.signUrl(BlobInfo.newBuilder(bucketName, filename)
				.build(), SIGNED_URL_TTL, SIGNED_URL_TTL_UNIT);
		return signedUrl.toString();
	}

	public String getSignedUploadUrl(	@Valid @NonNull User user,
										@NotBlank String blobName) {
		String bucketName = user.getBucket();
//		var blobInfo = BlobInfo.newBuilder(bucketName, blobName).setContentType("text/csv");	// only with SignUrlOption.withContentType()
		URL signedUrl = cloudStorage.signUrl(BlobInfo.newBuilder(bucketName, blobName)
				.build(), SIGNED_URL_TTL, SIGNED_URL_TTL_UNIT, SignUrlOption.httpMethod(HttpMethod.PUT));
		return signedUrl.toString();
	}

//	public String getSignedUploadUrl() {
//		String bucketName = "pvtdogpark";
//		String blobName = "hundrastplatser.csv";
//
//		URL signedUrl = cloudStorage.signUrl(BlobInfo.newBuilder(bucketName, blobName)
//				.build(), SIGNED_URL_TTL, SIGNED_URL_TTL_UNIT, SignUrlOption.httpMethod(HttpMethod.PUT));
//		return signedUrl.toString();
//	}
//
//	public String getSignedUrl() {
//		String bucketName = "pvtdogpark";
//		String blobName = "wastebin.csv";
//		URL signedUrl = cloudStorage.signUrl(BlobInfo.newBuilder(bucketName, blobName)
//				.build(), 7, TimeUnit.DAYS);
//		return signedUrl.toString();
//	}

	// for debug only
	public void displayAllBuckets() {
		final Page<Bucket> buckets = cloudStorage.list();
		for (Bucket bucket : buckets.iterateAll()) {
			System.out.println("Bucket: " + bucket.getName());
		}
	}

	public Bucket createRandomBucket() {
		return createBucket(STORAGE_CLASS, LOCATION, getRandomBucketName());
	}

	private Bucket createBucket(@NonNull StorageClass storageClass,
								@NotBlank String location,
								@NotBlank String bucketname) {
		final Bucket bucket = cloudStorage.create(BucketInfo.newBuilder(bucketname)
				.setLocation(location)
				.setStorageClass(storageClass)
				.build());
		log.info(String.format("Created bucket %s in %s with storage class %s.",
				bucket.getName(),
				bucket.getLocation(),
				bucket.getStorageClass()));
		return bucket;
	}

	private String getRandomBucketName() {
		return UUID.randomUUID()
				.toString();
	}

	public void deleteBucket(@NotBlank String bucketName) {
		final Bucket bucket = cloudStorage.get(bucketName);
		bucket.delete();
		log.info(String.format("Bucket %s was deleted", bucket.getName()));
	}

}
