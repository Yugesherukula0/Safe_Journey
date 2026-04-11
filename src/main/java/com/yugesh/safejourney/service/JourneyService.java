package com.yugesh.safejourney.service;

import com.yugesh.safejourney.dto.AlertRequest;
import com.yugesh.safejourney.dto.EndJourneyRequest;
import com.yugesh.safejourney.dto.JourneyResponse;
import com.yugesh.safejourney.dto.StartJourneyRequest;

public interface JourneyService {

    JourneyResponse startJourney(StartJourneyRequest request);

    JourneyResponse endJourney(EndJourneyRequest request);

    JourneyResponse triggerAlert(AlertRequest request);

    JourneyResponse getJourneyByToken(String trackingToken);
}