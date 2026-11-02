package com.portmanagement.service;

import java.util.List;

import com.portmanagement.dto.berth.BerthRequest;
import com.portmanagement.dto.berth.BerthResponse;

public interface BerthService {
	
	BerthResponse createBerth(BerthRequest request);
	List<BerthResponse> getAllBerths();
	BerthResponse getBerthById(Long id);
	BerthResponse updateBerth(Long id, BerthRequest request);
}
