package com.portmanagement.dto.berth;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BerthRequest {
	
	@NotBlank
	@Size(max = 100)
	private String berthName;
	
	@NotBlank
	@Size(max = 20)
	private String berthCode;
	
	@NotBlank
    @Size(max = 50)
	private String berthType;
	
	@NotNull
    @DecimalMin(value = "0.1")
	private Double length;
	
	@NotNull
    @DecimalMin(value = "0.1")
	private Double depth;
	
	@NotNull
	private Long portId;
}
