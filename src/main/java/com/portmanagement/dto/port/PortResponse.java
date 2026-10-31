
package com.portmanagement.dto.port;

import java.time.LocalDateTime;

import com.portmanagement.entity.PortStatus;

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
public class PortResponse {

    private Long id;
    private String name;
    private String portCode;
    private String city;
    private String state;
    private String country;
    private PortStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}