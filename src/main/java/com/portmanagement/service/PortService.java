package com.portmanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.portmanagement.dto.port.PortRequest;
import com.portmanagement.dto.port.PortResponse;

public interface PortService {
	
	PortResponse createPort(PortRequest request);
	List<PortResponse> getAllPorts();
	PortResponse getPortById(Long id);
	PortResponse updatePort(Long id, PortRequest request);
	void deletePort(Long id);
	List<PortResponse> searchPorts(String keyword);
	Page<PortResponse> getAllPortsPaged(int page, int size, String sortBy, String direction);
}
