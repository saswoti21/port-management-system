package com.portmanagement.dto.berth;

import java.time.LocalDateTime;

import com.portmanagement.entity.BerthStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BerthResponse {
	
	private Long id;
	private String berthName;
	private String berthCode;
	private String berthType;
	private Double length;
	private Double depth;
	private BerthStatus status;
	private Long portId;
	private String portName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
