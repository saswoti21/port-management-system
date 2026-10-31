package com.portmanagement.service;

import java.util.List;

import com.portmanagement.dto.LoginRequest;
import com.portmanagement.dto.LoginResponse;
import com.portmanagement.dto.UpdateUserRequest;
import com.portmanagement.dto.UserRequest;
import com.portmanagement.dto.UserResponse;

public interface UserService {
	UserResponse registerUser(UserRequest userRequest);
	LoginResponse loginUser(LoginRequest request);
	List<UserResponse> getAllUsers();
	UserResponse getUserById(Long id);
	UserResponse updateUser(Long id, UpdateUserRequest request);
	void activateUser(Long id);
	void deactivateUser(Long id);
	UserResponse getCurrentUser();
}
