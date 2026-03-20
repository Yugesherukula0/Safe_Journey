package com.yugesh.safejourney.service;

import com.yugesh.safejourney.entities.User;

public interface UserService {
	
	public User getByEmail(String email);

}
