package com.portmanagement.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.portmanagement.dto.berth.BerthRequest;
import com.portmanagement.dto.berth.BerthResponse;
import com.portmanagement.entity.BerthStatus;

public interface BerthService {
	
	BerthResponse createBerth(BerthRequest request);
	List<BerthResponse> getAllBerths();
	BerthResponse getBerthById(Long id);
	BerthResponse updateBerth(Long id, BerthRequest request);
	void deleteBerth(Long id);
	List<BerthResponse> searchBerthsByName(String name);
	Page<BerthResponse> getAllBerths(Pageable pageable);
	List<BerthResponse> getBerthsByPortId(Long portId);
	List<BerthResponse> getBerthsByStatus(BerthStatus status);
	BerthResponse updateBerthStatus(Long id, BerthStatus status);
}
