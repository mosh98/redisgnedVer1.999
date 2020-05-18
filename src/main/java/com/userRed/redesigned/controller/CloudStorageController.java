package com.userRed.redesigned.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userRed.redesigned.cloudstorage.CloudStorageService;

import lombok.val;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(path = "/cloud")
public class CloudStorageController {

	@Autowired
	private CloudStorageService cloudStorageService;

	@PutMapping(path = "/{bucketname}")
	public String createBucket() throws IOException {
		val bucket = cloudStorageService.createRandomBucket();
		cloudStorageService.displayAllBuckets();
		return "Created bucket " + bucket.getName();
	}

	@DeleteMapping(path = "/{bucketname}")
	public String deleteBucket(@PathVariable String bucketname) {
		cloudStorageService.deleteBucket(bucketname);
		return "Deleted bucket " + bucketname;
	}
}
