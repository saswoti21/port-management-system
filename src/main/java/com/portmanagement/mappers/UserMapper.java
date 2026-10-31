package com.portmanagement.mappers;

import org.springframework.stereotype.Component;

import com.portmanagement.dto.UserRequest;
import com.portmanagement.dto.UserResponse;
import com.portmanagement.entity.User;


@Component
public class UserMapper {
	public User toEntity(UserRequest request) {
		return User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(request.getPassword())
				.role(request.getRole())
				.build();
	}
	
	public UserResponse userResponse(User user) {
		return UserResponse.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.role(user.getRole())
				.createdAt(user.getCreatedAt())
				.build();
	}
}
