package com.portmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	@NotBlank(message = "email should be provided")
	@Email(message = "Enter a valid email")
	private String email;
	
	@NotBlank(message = "password should be provide")
	private String password;
}
