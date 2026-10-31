package com.portmanagement.mappers;

import org.springframework.stereotype.Component;

import com.portmanagement.dto.berth.BerthRequest;
import com.portmanagement.dto.berth.BerthResponse;
import com.portmanagement.entity.Berth;

@Component
public class BerthMapper {
	
	public Berth toEntity(BerthRequest request) {
		return Berth.builder()
				.berthName(request.getBerthName())
				.berthCode(request.getBerthCode())
				.berthType(request.getBerthType())
				.length(request.getLength())
				.depth(request.getDepth())
				.build();
	}
	
	public BerthResponse toResponse(Berth berth) {
		return BerthResponse.builder()
                .id(berth.getId())
                .berthName(berth.getBerthName())
                .berthCode(berth.getBerthCode())
                .berthType(berth.getBerthType())
                .length(berth.getLength())
                .depth(berth.getDepth())
                .status(berth.getStatus())
                .portId(berth.getPort().getId())
                .portName(berth.getPort().getName())
                .createdAt(berth.getCreatedAt())
                .updatedAt(berth.getUpdatedAt())
                .build(); 
	}
}
