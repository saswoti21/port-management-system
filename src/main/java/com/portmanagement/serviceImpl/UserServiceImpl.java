package com.portmanagement.serviceImpl;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.portmanagement.dto.LoginRequest;
import com.portmanagement.dto.LoginResponse;
import com.portmanagement.dto.UpdateUserRequest;
import com.portmanagement.dto.UserRequest;
import com.portmanagement.dto.UserResponse;
import com.portmanagement.entity.User;
import com.portmanagement.exception.LoginFailureException;
import com.portmanagement.mappers.UserMapper;
import com.portmanagement.repository.UserRepository;
import com.portmanagement.security.JWTService;
import com.portmanagement.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final JWTService jwtService;
	
	@Override
	@Transactional
	public UserResponse registerUser(UserRequest userRequest) {
		if(userRepository.existsByEmail(userRequest.getEmail())) {
			throw new RuntimeException("User already exists with email "+ userRequest.getEmail());
		}
		User user=userMapper.toEntity(userRequest);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActive(true);
		User saveUser=userRepository.save(user);
		UserResponse response=userMapper.userResponse(saveUser);
		return response;
	}

	@Override
	public LoginResponse loginUser(LoginRequest request) {
		
		var optionalUser=userRepository.findByEmail(request.getEmail());
		if(optionalUser.isEmpty()) {
			throw new LoginFailureException("User not found with given email");
		}
		
		User user=optionalUser.get();
		if(!user.isActive()) {
			throw new IllegalStateException("User account is inactive");
		}
		if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			String accessToken=jwtService.generateToken(user);
			LoginResponse loginResponse=new LoginResponse(accessToken,userMapper.userResponse(user));
			return loginResponse;
		}else {
			throw new LoginFailureException("Invalid email or password");
		}
	}

	@Override
	public List<UserResponse> getAllUsers() {
		List<User> users=userRepository.findAll();
		List<UserResponse> userResponse=users.stream()
				.map(userMapper::userResponse)
				.collect(Collectors.toList());
				
		return userResponse;
	}

	@Override
	public UserResponse getUserById(Long id) {
		User user=userRepository.findById(id)
				.orElseThrow(()->
				new NoSuchElementException("User not found with id: "+ id));
		UserResponse userResponse=userMapper.userResponse(user);
		return userResponse;
	}

	@Override
	@Transactional
	public UserResponse updateUser(Long id, UpdateUserRequest request) {
		User user=userRepository.findById(id)
				.orElseThrow(()->
				new NoSuchElementException("User not found with id: "+id));
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		User updateUser=userRepository.save(user);
		UserResponse userResponse=userMapper.userResponse(updateUser);
		return userResponse;
	}
	
	@Override
	public void activateUser(Long id) {
	    User user = userRepository.findById(id)
	            .orElseThrow(() ->
	                    new NoSuchElementException(
	                            "User not found with id: " + id));

	    user.setActive(true);
	    userRepository.save(user);
	}
	
	@Override
	public void deactivateUser(Long id) {
	    User user = userRepository.findById(id)
	            .orElseThrow(() ->
	                    new NoSuchElementException(
	                            "User not found with id: " + id));
	    user.setActive(false);
	    userRepository.save(user);
	}

	@Override
	public UserResponse getCurrentUser() {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();
	    String email = authentication.getName();
	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() ->
	                    new NoSuchElementException("User not found"));
	    return userMapper.userResponse(user);
	}
	
}
