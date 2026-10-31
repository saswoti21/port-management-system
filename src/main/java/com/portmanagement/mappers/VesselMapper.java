package com.portmanagement.mappers;

import org.springframework.stereotype.Component;

import com.portmanagement.dto.vessel.VesselRequest;
import com.portmanagement.dto.vessel.VesselResponse;
import com.portmanagement.entity.Vessel;

@Component
public class VesselMapper {
	
	public Vessel toEntity(VesselRequest request) {
		return Vessel.builder()
				.name(request.getName())
				.imoNumber(request.getImoNumber())
				.vesselType(request.getVesselType())
				.flagCountry(request.getFlagCountry())
				.capacity(request.getCapacity())
				.build();
	}
	
	public VesselResponse toResponse(Vessel vessel) {
		return VesselResponse.builder()
                .id(vessel.getId())
                .name(vessel.getName())
                .imoNumber(vessel.getImoNumber())
                .vesselType(vessel.getVesselType())
                .flagCountry(vessel.getFlagCountry())
                .capacity(vessel.getCapacity())
                .status(vessel.getStatus())
                .createdAt(vessel.getCreatedAt())
                .updatedAt(vessel.getUpdatedAt())
                .build();
	}
}
