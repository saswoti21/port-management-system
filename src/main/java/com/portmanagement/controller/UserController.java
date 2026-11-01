package com.portmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portmanagement.dto.ApiResponse;
import com.portmanagement.dto.UpdateUserRequest;
import com.portmanagement.dto.UserResponse;
import com.portmanagement.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	
	private final UserService userService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
		List<UserResponse> userResponse=userService.getAllUsers();
		ApiResponse<List<UserResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"Users fetched Successfully",
				userResponse);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id){
		UserResponse userResponse=userService.getUserById(id);
		ApiResponse<UserResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"User Retrieved Successfully with id",
				userResponse);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id,@Valid @RequestBody UpdateUserRequest request){
		UserResponse userResponse=userService.updateUser(id, request);
		ApiResponse<UserResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"User Updated Successfully",
				userResponse);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	
	@PutMapping("/{id}/activate")
	public ResponseEntity<ApiResponse<Void>> activateUser(
	        @PathVariable Long id) {

	    userService.activateUser(id);

	    ApiResponse<Void> response = new ApiResponse<>(
	            HttpStatus.OK.value(),
	            "User Activated Successfully",
	            null
	    );

	    return ResponseEntity.ok(response);
	}
	
	
	@DeleteMapping("/{id}/deactivate")
	public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable Long id) {
	    userService.deactivateUser(id);
	    ApiResponse<Void> apiResponse = new ApiResponse<>(
	            HttpStatus.OK.value(),
	            "User Deactivated Successfully",
	            null
	    );
	    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserResponse>> getCurrentuser(){
		UserResponse user=userService.getCurrentUser();
		ApiResponse<UserResponse> apiResponse=new ApiResponse<>(
				HttpStatus.OK.value(),
				"Current user fetched successfully",
				user);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
}
