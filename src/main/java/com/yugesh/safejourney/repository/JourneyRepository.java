package com.yugesh.safejourney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yugesh.safejourney.entities.Journey;
import com.yugesh.safejourney.entities.JourneyStatus;


@Repository
public interface JourneyRepository extends JpaRepository<Journey , Long>{
	
//	Unique
//	Public-facing (shared with others)
//	Used to track journey status
	public Journey findByTrackingToken(Long trackingToken);
	
	
//	Check if user already has active journey
//	Prevent multiple tracking sessions
	public Journey findByUserIdAndStatus(Long userId, JourneyStatus status);


}
