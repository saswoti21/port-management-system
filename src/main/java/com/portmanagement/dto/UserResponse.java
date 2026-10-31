package com.portmanagement.dto;

import java.time.LocalDateTime;

import com.portmanagement.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
	private Long id;
	private String name;
	private String email;
	private Role role;
	private LocalDateTime createdAt;
}
