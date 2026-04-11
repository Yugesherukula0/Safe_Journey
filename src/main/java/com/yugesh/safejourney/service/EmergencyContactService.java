package com.yugesh.safejourney.service;

import java.util.List;

import com.yugesh.safejourney.dto.ContactRequest;
import com.yugesh.safejourney.dto.ContactResponse;

public interface EmergencyContactService {

    void addContact(ContactRequest request);

    List<ContactResponse> getMyContacts();

    void updateContact(Long contactId, ContactRequest request);

    void deleteContact(Long contactId);
}