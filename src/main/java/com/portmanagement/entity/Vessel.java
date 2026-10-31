package com.portmanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="vessels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vessel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Vessel name is required")
	@Size(max = 100, message = "Vessel name cannot exceed 100 characters")
	@Column(nullable = false, length = 100)
	private String name;
	
	@NotBlank(message = "IMO number is required")
	@Pattern(
			 regexp = "^IMO[0-9]{7}$",
		     message = "IMO number must follow the format IMO followed by 7 digits"
			)
	@Column(nullable = false, unique = true, length = 10)
	private String imoNumber;
	
	@NotBlank(message = "Vessel type is required")
	@Size(max = 50, message = "Vessel type cannot exceed 50 characters")
	@Column(nullable = false, length = 50)
	private String vesselType;
	
	@NotBlank(message = "Flag country is required")
	@Size(max = 100, message = "Flag country cannot exceed 100 characters")
	@Column(nullable = false, length = 100)
	private String flagCountry;
	
	@NotNull(message = "Capacity is required")
	@Min(value = 1, message = "Capacity must be greater than zero")
	@Column(nullable = false)
	private Integer capacity;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private VesselStatus status;
	
	@Column(nullable = false, updatable = false, name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(nullable = false, name = "updated_at")
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void beforeSave() {
		LocalDateTime now=LocalDateTime.now();
		createdAt=now;
		updatedAt=now;
		
		if(status==null) {
			status=VesselStatus.ACTIVE;
		}
	}
	
	@PreUpdate
	public void beforeUpdate() {
		updatedAt=LocalDateTime.now();
	}
}
