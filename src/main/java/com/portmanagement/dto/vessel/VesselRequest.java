package com.portmanagement.dto.vessel;

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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VesselRequest {

    @NotBlank(message = "Vessel name is required")
    @Size(max = 100, message = "Vessel name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "IMO number is required")
    @Pattern(
        regexp = "^IMO[0-9]{7}$",
        message = "IMO number must follow the format IMO followed by 7 digits"
    )
    private String imoNumber;

    @NotBlank(message = "Vessel type is required")
    @Size(max = 50, message = "Vessel type cannot exceed 50 characters")
    private String vesselType;

    @NotBlank(message = "Flag country is required")
    @Size(max = 100, message = "Flag country cannot exceed 100 characters")
    private String flagCountry;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be greater than zero")
    private Integer capacity;
}


