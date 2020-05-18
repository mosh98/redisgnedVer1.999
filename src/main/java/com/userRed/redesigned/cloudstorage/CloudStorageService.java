package com.userRed.redesigned.cloudstorage;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageClass;

import lombok.NonNull;
import lombok.extern.java.Log;

@Log
@Service
public class CloudStorageService {

	private static final StorageClass STORAGE_CLASS = StorageClass.STANDARD;
	private static final String LOCATION = "eu";

	@Autowired
	Storage cloudStorage;

	public CloudStorageService(Storage cloudStorage) {
		this.cloudStorage = cloudStorage;
	}

	// for debug only
	public void displayAllBuckets() {
		final Page<Bucket> buckets = cloudStorage.list();
		for (Bucket bucket : buckets.iterateAll()) {
			System.out.println("Bucket: " + bucket.getName());
		}
	}

	public Bucket createRandomBucket() {
		return createBucketWithStorageClassAndLocation(STORAGE_CLASS, LOCATION);
	}

	private Bucket createBucketWithStorageClassAndLocation(	@NonNull StorageClass storageClass,
															@NotBlank String location) {
		final Bucket bucket = cloudStorage.create(BucketInfo.newBuilder(getRandomBucketName())
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
