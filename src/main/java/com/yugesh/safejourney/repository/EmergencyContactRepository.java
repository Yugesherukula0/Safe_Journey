package com.yugesh.safejourney.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yugesh.safejourney.entities.EmergencyContact;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact , Long> {
	
	 List<EmergencyContact> findByUserId(Long userId);

}
