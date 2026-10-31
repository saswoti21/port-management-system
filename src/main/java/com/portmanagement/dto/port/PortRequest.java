
package com.portmanagement.dto.port;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortRequest {

    @NotBlank(message = "Port name is required")
    @Size(max = 100, message = "Port name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Port code is required")
    @Size(max = 20, message = "Port code cannot exceed 20 characters")
    private String portCode;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;
}