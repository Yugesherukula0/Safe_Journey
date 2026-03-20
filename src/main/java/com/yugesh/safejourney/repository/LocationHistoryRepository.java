package com.yugesh.safejourney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yugesh.safejourney.entities.LocationHistory;

public interface LocationHistoryRepository extends JpaRepository<LocationHistory, Long>{
	
	public LocationHistory findTopByJourneyIdOrderByRecordedAtDesc(Long journeyId);

}
