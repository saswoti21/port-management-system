package com.portmanagement.service;

import com.portmanagement.dto.berth.BerthRequest;
import com.portmanagement.dto.berth.BerthResponse;

public interface BerthService {
	
	BerthResponse createBerth(BerthRequest request);
}
