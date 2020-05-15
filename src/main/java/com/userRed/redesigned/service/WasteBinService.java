package com.userRed.redesigned.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userRed.redesigned.model.WasteBin;
import com.userRed.redesigned.repository.WasteBinRepository;

@Service
public class WasteBinService {

	@Autowired
	private WasteBinRepository wasteBinRepository;

	public Long count() {
		return wasteBinRepository.count();
	}

	public List<WasteBin> findAll() {
		return wasteBinRepository.findAll();
	}

	public List<WasteBin> findByMaxDistanceFromPosition(Double latitude,
														Double longitude,
														Double distance) {
		return wasteBinRepository.findByMaxDistanceFromPosition(latitude, longitude, distance);
	}
}
