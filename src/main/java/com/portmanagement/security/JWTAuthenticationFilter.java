package com.portmanagement.security;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.portmanagement.entity.User;
import com.portmanagement.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	private final JWTService jwtService;
	private final UserRepository userRepository;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader=request.getHeader("Authorization");
//		System.out.println(authHeader);
		
		// Check whether the Authorization header contains JWT
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//Extract JWT token from header
		String jwtToken=authHeader.substring(7);
		if(!jwtService.isTokenValid(jwtToken)) {
			filterChain.doFilter(request, response);
			return;
		}
		String email=jwtService.extractUserName(jwtToken);
		Optional<User> optionalUser=userRepository.findByEmail(email);
		if(optionalUser.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}
		User user=optionalUser.get();
		if (!user.isActive()) {
		    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		    return;
		}
		String role=user.getRole().name();
		
		List<GrantedAuthority> authorities=List.of(new SimpleGrantedAuthority("ROLE_"+role));
		UsernamePasswordAuthenticationToken authentication=
				new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}
