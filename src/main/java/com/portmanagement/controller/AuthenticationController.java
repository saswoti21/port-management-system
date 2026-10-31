package com.portmanagement.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portmanagement.dto.ApiResponse;
import com.portmanagement.dto.LoginRequest;
import com.portmanagement.dto.LoginResponse;
import com.portmanagement.dto.UserRequest;
import com.portmanagement.dto.UserResponse;
import com.portmanagement.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody UserRequest userRequest) {
		UserResponse serviceResponse=userService.registerUser(userRequest);
		ApiResponse<UserResponse> apiResponse=new ApiResponse<UserResponse>(HttpStatus.CREATED.value(), "User registered successfully", serviceResponse);
		return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@Valid @RequestBody LoginRequest request){
		LoginResponse serviceResponse=userService.loginUser(request);
		ApiResponse<LoginResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),"Login successful",serviceResponse);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
}
