package com.portmanagement.serviceImpl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.portmanagement.dto.vessel.VesselRequest;
import com.portmanagement.dto.vessel.VesselResponse;
import com.portmanagement.entity.Vessel;
import com.portmanagement.entity.VesselStatus;
import com.portmanagement.mappers.VesselMapper;
import com.portmanagement.repository.VesselRepository;
import com.portmanagement.service.VesselService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VesselServiceImpl implements VesselService{
	
	private final VesselRepository vesselRepository;
	private final VesselMapper vesselMapper;
	
	@Override
	public VesselResponse createVessel(VesselRequest request) {
		
		if(vesselRepository.existsByImoNumber(request.getImoNumber())) {
			throw new IllegalArgumentException("Vessel with this IMO number already exists");
		}
		Vessel vessel=vesselMapper.toEntity(request);
		Vessel savedVessel=vesselRepository.save(vessel);
		VesselResponse response=vesselMapper.toResponse(savedVessel);
		return response;
	}

	@Override
	public List<VesselResponse> getAllVesssel() {
		List<Vessel> vessels=vesselRepository.findAll();
		List<VesselResponse> vesselResponse=vessels.stream()
				.map(vesselMapper::toResponse)
				.collect(Collectors.toList());
		return vesselResponse;
	}

	@Override
	public VesselResponse getVesselById(Long id) {
		Vessel vessel=vesselRepository.findById(id)
				.orElseThrow(()->new NoSuchElementException(
						"Vessel not found with id: "+id));
		VesselResponse vesselResponse=vesselMapper.toResponse(vessel);
		return vesselResponse;
	}

	@Override
	@Transactional
	public VesselResponse updateVessel(Long id, VesselRequest request) {
		Vessel vessel=vesselRepository.findById(id)
				.orElseThrow(()->new NoSuchElementException(
						"Vessel not found with id: "+id));
		
		boolean duplicateImo=vesselRepository
				.findByImoNumber(request.getImoNumber())
				.filter(existing->!existing.getId().equals(id))
				.isPresent();
		
		if(duplicateImo) {
			throw new IllegalArgumentException("Another vessel already uses this IMO number");
		}
		
		vessel.setName(request.getName());
		vessel.setImoNumber(request.getImoNumber());
		vessel.setVesselType(request.getVesselType());
		vessel.setFlagCountry(request.getFlagCountry());
		vessel.setCapacity(request.getCapacity());
		
		Vessel updatedVessel=vesselRepository.save(vessel);
		
		VesselResponse vesselResponse=vesselMapper.toResponse(updatedVessel);
		return vesselResponse;
	}

	@Override
	public void deleteVessel(Long id) {
		Vessel vessel=vesselRepository.findById(id)
				.orElseThrow(()->new NoSuchElementException(
						"Vessel not found with id: "+id));
		vesselRepository.delete(vessel);
	}

	@Override
	public List<VesselResponse> searchVesselsByName(String name) {
		
		if(name==null || name.isBlank()) {
			throw new IllegalArgumentException(
					"Vessel name search keyword cannot be empty");
		}
		
		return vesselRepository.findByNameContainingIgnoreCase(name.trim())
				.stream()
				.map(vesselMapper::toResponse)
				.toList();
		
	}

	@Override
	public Page<VesselResponse> getAllVessels(Pageable pageable) {
		
		return vesselRepository.findAll(pageable)
				.map(vesselMapper::toResponse);
	}

	@Override
	public List<VesselResponse> getVesselByStatusList(VesselStatus status) {
		if(status == null) {
			throw new IllegalArgumentException("Vessel status cannot be null");
			
		}
		
		return vesselRepository.findByStatus(status)
				.stream()
				.map(vesselMapper::toResponse)
				.toList();
	}

	@Override
	public VesselResponse updateVesselStatus(Long id, VesselStatus status) {
		
		if(status == null) {
			throw new IllegalArgumentException("vessel status cannot be null");
		}
		
		Vessel vessel=vesselRepository.findById(id)
				.orElseThrow(()->new NoSuchElementException("Vessel not found with id: "+id));
		
		vessel.setStatus(status);
		Vessel updatedVessel=vesselRepository.save(vessel);
		
		return vesselMapper.toResponse(updatedVessel);
	}

}
