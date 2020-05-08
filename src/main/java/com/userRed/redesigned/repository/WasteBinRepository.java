package com.userRed.redesigned.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.userRed.redesigned.model.WasteBin;

public interface WasteBinRepository extends CrudRepository<WasteBin, Long> {

	public List<WasteBin> findAll();

	@Query("FROM WasteBin WHERE "
			+ "(6371392.896 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(latitude))))"
			+ " < :maxDistance ORDER BY "
			+ "(6371392.896 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(latitude))))"
			+ " DESC")
	List<WasteBin> findByMaxDistanceFromPosition(	Double latitude,
													Double longitude,
													Double maxDistance);

}
