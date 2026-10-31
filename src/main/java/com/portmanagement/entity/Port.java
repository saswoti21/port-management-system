package com.portmanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ports")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Port {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Port name is required")
	@Size(max = 100,message = "Port name cannot exceed 100 characters")
	@Column(nullable = false,length = 100)
	private String name;
	
	@NotBlank(message = "Port code is required")
	@Size(max = 20,message = "Port code cannot exceed 20 characters")
	@Column(nullable = false,unique = true,length = 20)
	private String portCode;
	
	@NotBlank(message = "City is required")
	@Column(nullable = false)
	private String city;
	
	@NotBlank(message = "State is required")
	@Column(nullable = false)
	private String state;
	
	@NotBlank(message = "Country is required")
	@Column(nullable = false)
	private String country;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false,length = 30)
	private PortStatus status;
	
	@Column(nullable = false,updatable = false, name = "created_At")
	private LocalDateTime createdAt;
	
	@Column(nullable = false,name = "updated_At")
	private LocalDateTime updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;
	
	@PrePersist
	public void beforeSave() {
		LocalDateTime now=LocalDateTime.now();
		createdAt=now;
		updatedAt=now;
		
		if(status==null) {
			status=PortStatus.ACTIVE;
		}
	}
	
	@PreUpdate
	public void beforeUpdate() {
		updatedAt=LocalDateTime.now();
	}
	
}
