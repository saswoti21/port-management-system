package com.portmanagement.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portmanagement.dto.ApiResponse;
import com.portmanagement.dto.berth.BerthRequest;
import com.portmanagement.dto.berth.BerthResponse;
import com.portmanagement.entity.BerthStatus;
import com.portmanagement.service.BerthService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
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
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<BerthResponse>>> getAllBerths(){
		List<BerthResponse> response=berthService.getAllBerths();
		ApiResponse<List<BerthResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(), "Berth retrieved successfully", response);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<BerthResponse>> getBerthById(@PathVariable Long id){
		BerthResponse response=berthService.getBerthById(id);
		ApiResponse<BerthResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(), "Berth retrieved successfully", response);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<BerthResponse>> updateBerth(Long id, BerthRequest request){
		BerthResponse response=berthService.updateBerth(id, request);
		ApiResponse<BerthResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(), "Berth updated successfully", response);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteBerth(@PathVariable Long id){
		berthService.deleteBerth(id);
		ApiResponse<Void> apiResponse=new ApiResponse<>(HttpStatus.OK.value(), "Berth Deleted Sucessfully", null);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<BerthResponse>>> searchBerthsByName(@RequestParam String name){
		List<BerthResponse> response=berthService.searchBerthsByName(name);
		ApiResponse<List<BerthResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(), "Berth search completed successfully", response);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/page")
	public ResponseEntity<ApiResponse<Page<BerthResponse>>> getAllBerths( @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction){
		
		if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }
		
		Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
		
		Pageable pageable = PageRequest.of(page, size, sort);

        Page<BerthResponse> response =
                berthService.getAllBerths(pageable);
        ApiResponse<Page<BerthResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(), "Berths retrieved successfuly", response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	
	@GetMapping("/port/{portId}")
    public ResponseEntity<ApiResponse<List<BerthResponse>>> getBerthsByPortId(
            @PathVariable Long portId) {

        List<BerthResponse> response = berthService.getBerthsByPortId(portId);
        ApiResponse<List<BerthResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(), "Berth retrieved succesfully", response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ApiResponse<List<BerthResponse>>> getBerthsByStatus(@PathVariable BerthStatus status){
		
		List<BerthResponse> response = berthService.getBerthsByStatus(status);
		ApiResponse<List<BerthResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(), "Berth fetched successfully by status", response);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@PatchMapping("/{id}/status")
	public ResponseEntity<ApiResponse<BerthResponse>> updateBerthStatus(@PathVariable Long id, @RequestParam BerthStatus status){
		
		BerthResponse response=berthService.updateBerthStatus(id, status);
		ApiResponse<BerthResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(), "Berth updated Successfully", response);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}


