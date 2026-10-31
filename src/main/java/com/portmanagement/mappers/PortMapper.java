package com.portmanagement.mappers;

import org.springframework.stereotype.Component;

import com.portmanagement.dto.port.PortRequest;
import com.portmanagement.dto.port.PortResponse;
import com.portmanagement.entity.Port;

@Component
public class PortMapper {
	
	public Port toEntity(PortRequest request) {
		return Port.builder()
				.name(request.getName())
				.portCode(request.getPortCode())
				.city(request.getCity())
				.state(request.getState())
				.country(request.getCountry())
				.build();
	}
	
	public PortResponse toResponse(Port port) {
		return PortResponse.builder()
				.id(port.getId())
				.name(port.getName())
				.portCode(port.getPortCode())
				.city(port.getCity())
				.state(port.getState())
				.country(port.getCountry())
				.status(port.getStatus())
				.createdAt(port.getCreatedAt())
				.updatedAt(port.getUpdatedAt())
				.build();
	}
}
