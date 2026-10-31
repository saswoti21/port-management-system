package com.portmanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.portmanagement.dto.vessel.VesselRequest;
import com.portmanagement.dto.vessel.VesselResponse;
import com.portmanagement.entity.VesselStatus;

public interface VesselService {
	
	VesselResponse createVessel(VesselRequest request);
	List<VesselResponse> getAllVesssel();
	VesselResponse getVesselById(Long id);
	VesselResponse updateVessel(Long id, VesselRequest request);
	void deleteVessel(Long id);
	List<VesselResponse> searchVesselsByName(String name);
	Page<VesselResponse> getAllVessels(Pageable pageable);
	List<VesselResponse> getVesselByStatusList(VesselStatus status);
	VesselResponse updateVesselStatus(Long id, VesselStatus status);
}
