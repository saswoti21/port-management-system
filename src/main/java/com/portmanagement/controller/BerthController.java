package com.portmanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portmanagement.dto.ApiResponse;
import com.portmanagement.dto.berth.BerthRequest;
import com.portmanagement.dto.berth.BerthResponse;
import com.portmanagement.service.BerthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/berths")
@RequiredArgsConstructor
public class BerthController {
	
	private final BerthService berthService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<BerthResponse>> createBerth(@Valid @RequestBody BerthRequest request){
		
		BerthResponse response=berthService.createBerth(request);
		ApiResponse<BerthResponse> apiResponse=new ApiResponse<>(HttpStatus.CREATED.value(), "Berth created successfully", response);
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}
}
