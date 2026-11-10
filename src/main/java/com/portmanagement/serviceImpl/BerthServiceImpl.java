package com.portmanagement.serviceImpl;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.portmanagement.dto.berth.BerthRequest;
import com.portmanagement.dto.berth.BerthResponse;
import com.portmanagement.entity.Berth;
import com.portmanagement.entity.BerthStatus;
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

	@Override
	public List<BerthResponse> getAllBerths() {
		
		return berthRepository.findAll()
				.stream()
				.map(berthMapper::toResponse)
				.toList();
	}

	@Override
	public BerthResponse getBerthById(Long id) {
		Berth berth=berthRepository.findById(id)
				.orElseThrow(()-> new NoSuchElementException("Berth not found with id: "+id));
		return berthMapper.toResponse(berth);
	}

	@Override
	public BerthResponse updateBerth(Long id, BerthRequest request) {
		
		Berth berth=berthRepository.findById(id)
				.orElseThrow(()->new NoSuchElementException("Berth not found with id: "+ id));
		berthRepository.findByBerthCode(request.getBerthCode())
				.filter(existing->!existing.getId().equals(id))
				.ifPresent(existing -> {
					throw new IllegalArgumentException("Berth with this code already exists");
				});
		
		Port port=portRepository.findById(request.getPortId())
				.orElseThrow(()->new NoSuchElementException("Port not found with id: "+request.getPortId()));
		
		berth.setBerthName(request.getBerthName());
        berth.setBerthCode(request.getBerthCode());
        berth.setBerthType(request.getBerthType());
        berth.setLength(request.getLength());
        berth.setDepth(request.getDepth());
        berth.setPort(port);
        
        Berth updatedBerth = berthRepository.save(berth);
        return berthMapper.toResponse(updatedBerth);
	}

	 @Override
	    public void deleteBerth(Long id) {
	        Berth berth = berthRepository.findById(id)
	                .orElseThrow(() -> new NoSuchElementException(
	                        "Berth not found with id: " + id));
	        berthRepository.delete(berth);
	    }

	@Override
	public List<BerthResponse> searchBerthsByName(String name) {
		
		if(name==null || name.isBlank()) {
			throw new IllegalArgumentException("Berth name cannot be empty");
			
		}
		return berthRepository.findByBerthNameContainingIgnoreCase(name.trim())
				.stream()
				.map(berthMapper::toResponse)
				.toList();
	}

	
	@Override
	public Page<BerthResponse> getAllBerths(Pageable pageable) {
		
		return berthRepository.findAll(pageable)
				.map(berthMapper::toResponse);
	}

	@Override
	public List<BerthResponse> getBerthsByPortId(Long portId) {
		if(!portRepository.existsById(portId)) {
			throw new NoSuchElementException("Port not found with id: "+ portId);
		}
		return berthRepository.findByPortId(portId)
				.stream()
				.map(berthMapper::toResponse)
				.toList();
	}

	@Override
	public List<BerthResponse> getBerthsByStatus(BerthStatus status) {

        if (status == null) {
            throw new IllegalArgumentException(
                    "Berth status cannot be null");
        }
        return berthRepository.findByStatus(status)
                .stream()
                .map(berthMapper::toResponse)
                .toList();
    }

	@Override
    public BerthResponse updateBerthStatus(Long id, BerthStatus status) {

        if (status == null) {
            throw new IllegalArgumentException(
                    "Berth status cannot be null");
        }

        Berth berth = berthRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Berth not found with id: " + id));

        berth.setStatus(status);

        Berth updatedBerth = berthRepository.save(berth);

        return berthMapper.toResponse(updatedBerth);
    }
	
}
