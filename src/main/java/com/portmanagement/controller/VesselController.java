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
import com.portmanagement.dto.vessel.VesselRequest;
import com.portmanagement.dto.vessel.VesselResponse;
import com.portmanagement.entity.VesselStatus;
import com.portmanagement.service.VesselService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/vessels")
@RequiredArgsConstructor
public class VesselController {
	
	private final VesselService vesselService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<VesselResponse>> createVessel(@Valid @RequestBody VesselRequest request){
		VesselResponse vesselReponse=vesselService.createVessel(request);
		ApiResponse<VesselResponse> apiResponse=new ApiResponse<>(HttpStatus.CREATED.value(), "Vessel Created", vesselReponse);
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<VesselResponse>>> getAllVessels(){
		List<VesselResponse> vessels=vesselService.getAllVesssel();
		ApiResponse<List<VesselResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"Vessels fetched successfully", vessels);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<VesselResponse>> getVesselsById(@PathVariable Long id){
		VesselResponse vesselResponse=vesselService.getVesselById(id);
		ApiResponse<VesselResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"Vessel fetched with given id: ", vesselResponse);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<VesselResponse>> updateVessel(@PathVariable Long id,@RequestBody VesselRequest request){
		VesselResponse vesselResponse=vesselService.updateVessel(id, request);
		ApiResponse<VesselResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"Vessel Updated Successfully", vesselResponse);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<VesselResponse>> deleteVessel(@PathVariable Long id){
		vesselService.deleteVessel(id);
		ApiResponse<VesselResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"Vessel deleted successfully", null);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<VesselResponse>>> searchVesselByName(@RequestParam String name){
		List<VesselResponse> vessels=vesselService.searchVesselsByName(name);
		ApiResponse<List<VesselResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),
				"Vessels Searched successfully", vessels);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	
	@GetMapping("/page")
	public ResponseEntity<ApiResponse<Page<VesselResponse>>> getAllVesselPaginated(@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction){
		
		if(page<0) {
			throw new IllegalArgumentException("Page number cannot be negative");
		}
		
		if(size<1 || size>100) {
			throw new IllegalArgumentException("Page size must be between 1 and 100");
		}
		
		Sort sort = direction.equalsIgnoreCase("desc")
				? Sort.by(sortBy).descending()
						: Sort.by(sortBy).ascending();
		
		Pageable pageable=PageRequest.of(page, size, sort);
		
		Page<VesselResponse> vessels=vesselService.getAllVessels(pageable);
		ApiResponse<Page<VesselResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(), "Vessels fetchde successfully", vessels);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ApiResponse<List<VesselResponse>>> getVesselByStatusList(@PathVariable VesselStatus status){
		List<VesselResponse> vessels=vesselService.getVesselByStatusList(status);
		ApiResponse<List<VesselResponse>> apiResponse=new ApiResponse<>(HttpStatus.OK.value(),"Vessels Fetched Successfully By Status", vessels);
		return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@PatchMapping("/{id}/status")
	public ResponseEntity<ApiResponse<VesselResponse>> updateVesselStatus(@PathVariable Long id, @RequestParam VesselStatus status){
		VesselResponse vessel=vesselService.updateVesselStatus(id, status);
		ApiResponse<VesselResponse> apiResponse=new ApiResponse<>(HttpStatus.OK.value(), "Vessel Status updated Successfully", vessel);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}
