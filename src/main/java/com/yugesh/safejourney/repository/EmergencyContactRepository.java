package com.yugesh.safejourney.repository;

import com.yugesh.safejourney.entities.EmergencyContact;
import com.yugesh.safejourney.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {

    List<EmergencyContact> findByUser(User user);

    boolean existsByUserAndPhoneNumber(User user, String phoneNumber);

    boolean existsByUserAndEmail(User user, String email);
}