package com.portmanagement.serviceImpl;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.portmanagement.dto.berth.BerthRequest;
import com.portmanagement.dto.berth.BerthResponse;
import com.portmanagement.entity.Berth;
import com.portmanagement.entity.Port;
import com.portmanagement.mappers.BerthMapper;
import com.portmanagement.repository.BerthRepository;
import com.portmanagement.repository.PortRepository;
import com.portmanagement.service.BerthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BerthServiceImpl implements BerthService{
	
	private final BerthRepository berthRepository;
	private final PortRepository portRepository;
	private final BerthMapper berthMapper;
	
	@Override
	public BerthResponse createBerth(BerthRequest request) {
		
		if(berthRepository.existsByBerthCode(request.getBerthCode())) {
			throw new IllegalArgumentException("Berth with this code already exists");
		}
		
		Port port=portRepository.findById(request.getPortId())
				.orElseThrow(()->new NoSuchElementException("Port not found with id: "+request.getPortId()));
		
		Berth berth=berthMapper.toEntity(request);
		berth.setPort(port);
		
		Berth savesBerth=berthRepository.save(berth);
		BerthResponse response=berthMapper.toResponse(savesBerth);
		return response;
	}
	
}
