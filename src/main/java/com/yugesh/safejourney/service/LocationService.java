package com.yugesh.safejourney.service;

import com.yugesh.safejourney.dto.LocationRequest;
import com.yugesh.safejourney.dto.LocationResponse;

public interface LocationService {

    void saveLocation(LocationRequest request);

    LocationResponse getLatestLocation(String trackingToken);
}