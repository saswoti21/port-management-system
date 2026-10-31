package com.portmanagement.serviceImpl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.portmanagement.dto.port.PortRequest;
import com.portmanagement.dto.port.PortResponse;
import com.portmanagement.entity.Port;
import com.portmanagement.entity.User;
import com.portmanagement.mappers.PortMapper;
import com.portmanagement.repository.PortRepository;
import com.portmanagement.repository.UserRepository;
import com.portmanagement.service.PortService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortServiceImpl implements PortService{
	
	private final UserRepository userRepository;
	private final PortMapper portMapper;
	private final PortRepository portRepository;
	
	@Override
	@Transactional
	public PortResponse createPort(PortRequest request) {
		
		//get the currently authenticated user
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		
		//get the user's email from the authentication
		String email=authentication.getName();
		
		//find the user in the database
		Optional<User> optionalUser=userRepository.findByEmail(email);
		if(optionalUser.isEmpty()) {
			throw new NoSuchElementException("No user present with email "+ email);
		}
		
		//get the actual User object
		User creator=optionalUser.get();
		
		//convert request DTO into Port entity
		Port port=portMapper.toEntity(request);
		
		//Assign the loggedin user as the creator
		port.setCreatedBy(creator);
		
		//save the port in the database
		Port savedPort=portRepository.save(port);
		
		//convert saved entity into reponse DTO
		PortResponse response=portMapper.toResponse(savedPort);
		return response;
	}

	@Override
	public List<PortResponse> getAllPorts() {
		
		//retrieves all port records from the database
		List<Port> ports=portRepository.findAll();
		
		//let us process the list of ports one by one, converts each Port entity into a PortResponse DTO, collects the converted responses into list.
		List<PortResponse> portResponses=ports.stream()
				.map(portMapper::toResponse)
				.collect(Collectors.toList());
		
		//sends the list back to the controller
		return portResponses;
	}

	@Override
	public PortResponse getPortById(Long id) {
		Port port=portRepository.findById(id)
				.orElseThrow(()->
				new NoSuchElementException("Port not found with id: "+id));
		PortResponse portResponse=portMapper.toResponse(port);
		return portResponse;
	}

	@Override
	@Transactional
	public PortResponse updatePort(Long id, PortRequest request) {
		Port port=portRepository.findById(id)
				.orElseThrow(()->
				new NoSuchElementException("Port not found with id: "+id));
		if(!port.getPortCode().equals(request.getPortCode()) && portRepository.existsByPortCode(request.getPortCode())) {
			throw new IllegalArgumentException("Port code already exists");
		}
		
		port.setName(request.getName());
		port.setPortCode(request.getPortCode());
		port.setCity(request.getCity());
		port.setState(request.getState());
		port.setCountry(request.getCountry());
		
		Port updatedPort=portRepository.save(port);
		PortResponse portResponse=portMapper.toResponse(updatedPort);
		return portResponse;
	}

	@Override
	public void deletePort(Long id) {
	    Port port = portRepository.findById(id)
	            .orElseThrow(() ->
	                    new java.util.NoSuchElementException(
	                            "Port not found with id: " + id));

	    portRepository.delete(port);
	}

	@Override
	public List<PortResponse> searchPorts(String keyword) {
		List<Port> ports=
				portRepository.findByNameContainingIgnoreCaseOrPortCodeContainingIgnoreCaseOrCityContainingIgnoreCaseOrCountryContainingIgnoreCase(keyword,keyword,keyword,keyword);
		return ports.stream()
				.map(portMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public Page<PortResponse> getAllPortsPaged(int page, int size, String sortBy, String direction) {
		Sort sort=direction.equalsIgnoreCase("desc")
				? Sort.by(sortBy).descending()
						: Sort.by(sortBy).ascending();
		Pageable pageable=PageRequest.of(page, size, sort);
		Page<Port> ports=portRepository.findAll(pageable);
		return ports.map(portMapper::toResponse);
	}
	
}
