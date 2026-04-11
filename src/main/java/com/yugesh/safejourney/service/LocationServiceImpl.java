package com.yugesh.safejourney.service;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yugesh.safejourney.dto.LocationRequest;
import com.yugesh.safejourney.dto.LocationResponse;
import com.yugesh.safejourney.entities.Journey;
import com.yugesh.safejourney.entities.JourneyStatus;
import com.yugesh.safejourney.entities.LocationHistory;
import com.yugesh.safejourney.entities.User;
import com.yugesh.safejourney.exception.BadRequestException;
import com.yugesh.safejourney.exception.ResourceNotFoundException;
import com.yugesh.safejourney.exception.UnauthorizedException;
import com.yugesh.safejourney.repository.JourneyRepository;
import com.yugesh.safejourney.repository.LocationHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final JourneyRepository journeyRepository;
    private final LocationHistoryRepository locationRepository;

    @Override
    public void saveLocation(LocationRequest request) {

        // 1. Find journey
        Journey journey = journeyRepository.findByTrackingToken(request.getTrackingToken())
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found"));

//       2. Validate status
//       AUTH CHECK
        User user = getLoggedInUser();
        if (!journey.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You are not allowed to access this journey");
        }

        //STATUS CHECK
        if (journey.getStatus() == JourneyStatus.COMPLETED) {
            throw new BadRequestException("Journey already completed");
        }
        
     //  RATE LIMIT (basic)
        LocationHistory last = locationRepository
                .findTopByJourneyIdOrderByRecordedAtDesc(journey.getId())
                .orElse(null);

        if (last != null &&
                last.getRecordedAt().isAfter(LocalDateTime.now().minusSeconds(5))) {
            throw new BadRequestException("Too many location updates");
        }
        
        // 3. Save location
        LocationHistory location = LocationHistory.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .journey(journey)
                .build();

        locationRepository.save(location);
    }

    @Override
    public LocationResponse getLatestLocation(String trackingToken) {

        Journey journey = journeyRepository.findByTrackingToken(trackingToken)
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found"));
        
        User user = getLoggedInUser();
        if (!journey.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You are not allowed to access this journey");
        }
        
        LocationHistory latest = locationRepository
                .findTopByJourneyIdOrderByRecordedAtDesc(journey.getId())
                .orElseThrow(()-> new ResourceNotFoundException("No location data found"));

        

        return LocationResponse.builder()
                .latitude(latest.getLatitude())
                .longitude(latest.getLongitude())
                .recordedAt(latest.getRecordedAt())
                .build();
    }
    
    private User getLoggedInUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}