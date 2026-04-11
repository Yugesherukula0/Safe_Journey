package com.yugesh.safejourney.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yugesh.safejourney.dto.AlertRequest;
import com.yugesh.safejourney.dto.EndJourneyRequest;
import com.yugesh.safejourney.dto.JourneyResponse;
import com.yugesh.safejourney.dto.StartJourneyRequest;
import com.yugesh.safejourney.service.JourneyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/journey")
@RequiredArgsConstructor
public class JourneyController {

    private final JourneyService journeyService;

    // START JOURNEY
    @PostMapping("/start")
    public ResponseEntity<JourneyResponse> startJourney(
            @Valid @RequestBody StartJourneyRequest request) {

        return ResponseEntity.ok(journeyService.startJourney(request));
    }

    // END JOURNEY
    @PostMapping("/end")
    public ResponseEntity<JourneyResponse> endJourney(
            @Valid @RequestBody EndJourneyRequest request) {

        return ResponseEntity.ok(journeyService.endJourney(request));
    }

    // TRIGGER ALERT
    @PostMapping("/alert")
    public ResponseEntity<JourneyResponse> triggerAlert(
            @Valid @RequestBody AlertRequest request) {

        return ResponseEntity.ok(journeyService.triggerAlert(request));
    }

    // GET JOURNEY BY TOKEN
    @GetMapping("/{token}")
    public ResponseEntity<JourneyResponse> getJourney(
            @PathVariable String token) {

        return ResponseEntity.ok(journeyService.getJourneyByToken(token));
    }
}