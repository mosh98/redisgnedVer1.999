package com.userRed.redesigned.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userRed.redesigned.request.WasteBinRequest;
import com.userRed.redesigned.service.WasteBinService;

@RestController
@RequestMapping(path = "/wastebin")
public class WasteBinController {

	@Autowired
	private WasteBinService wasteBinService;

	@GetMapping(path = "/count")
	public ResponseEntity<?> count() {
		return ResponseEntity.ok(wasteBinService.count());
	}

	@GetMapping(path = "/find", params = "All")
	public ResponseEntity<?> findAll() {
		var wasteBins = wasteBinService.findAll();
		return ResponseEntity.ok(wasteBins);
	}

	@GetMapping(path = "/find")
	public ResponseEntity<?> findByMaxDistanceFromPositionBody(@Valid @RequestBody WasteBinRequest wasteBinRequest) {
		var wasteBins = wasteBinService.findByMaxDistanceFromPosition(wasteBinRequest.getLatitude(),
				wasteBinRequest.getLongitude(),
				wasteBinRequest.getMaxDistance());
		return ResponseEntity.ok(wasteBins);
	}

	@GetMapping(path = "/find", params = { "Latitude", "Longitude", "MaxDistance" })
	public ResponseEntity<?>
			findByMaxDistanceFromPositionParams(@NotBlank @RequestParam(name = "Latitude") Double latitude,
												@NotBlank @RequestParam(name = "Longitude") Double longitude,
												@NotBlank @RequestParam(name = "MaxDistance") Double maxDistance) {
		var wasteBins = wasteBinService.findByMaxDistanceFromPosition(latitude, longitude, maxDistance);
		return ResponseEntity.ok(wasteBins);
	}

}
