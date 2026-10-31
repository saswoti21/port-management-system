package com.portmanagement.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portmanagement.dto.ApiResponse;
import com.portmanagement.dto.port.PortRequest;
import com.portmanagement.dto.port.PortResponse;
import com.portmanagement.service.PortService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/ports")
@RequiredArgsConstructor
public class PortController {
	
	private final PortService portService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<PortResponse>> createPort(@Valid @RequestBody PortRequest request){
		PortResponse portResponse= portService.createPort(request);
		ApiResponse<PortResponse> apiResponse=new ApiResponse<>(HttpStatus.CREATED.value(),"Port Created", portResponse);
		return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<PortResponse>>> getAllPorts(){
		List<PortResponse> portResponses=portService.getAllPorts();
		ApiResponse<List<PortResponse>> apiResponse=
				new ApiResponse<>(HttpStatus.OK.value(),"Ports Retrieved Successfully",portResponses);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PortResponse>> getPortById(@PathVariable Long id){
		PortResponse portResponse=portService.getPortById(id);
		ApiResponse<PortResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"Port Retrieved successfully bt given id",portResponse);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<PortResponse>> updatePort(@PathVariable  Long id,@Valid @RequestBody PortRequest request){
		PortResponse portResponse=portService.updatePort(id,request);
		ApiResponse<PortResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"Port Updated successfully", portResponse);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deletePort(@PathVariable Long id) {
	    portService.deletePort(id);
	    ApiResponse<Void> apiResponse = new ApiResponse<>(
	            HttpStatus.OK.value(),
	            "Port Deleted Successfully",
	            null);
	    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<PortResponse>>> searchPorts(@RequestParam String keyword){
		List<PortResponse> portResponses=portService.searchPorts(keyword);
		ApiResponse<List<PortResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"Ports Search Completed Successfully",
				portResponses);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	@GetMapping("/page")
	public ResponseEntity<ApiResponse<Page<PortResponse>>> getAllPortsPaged(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String direction){
		
		Page<PortResponse> portResponses=portService.getAllPortsPaged(page, size, sortBy, direction);
		ApiResponse<Page<PortResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"Ports retrieved Successfully",
				portResponses);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}
