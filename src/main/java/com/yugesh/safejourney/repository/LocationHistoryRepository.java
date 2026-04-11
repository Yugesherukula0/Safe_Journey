package com.yugesh.safejourney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yugesh.safejourney.entities.LocationHistory;

public interface LocationHistoryRepository extends JpaRepository<LocationHistory, Long>{
	
	Optional<LocationHistory> findTopByJourneyIdOrderByRecordedAtDesc(Long journeyId);

}
