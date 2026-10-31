package com.portmanagement.dto.vessel;

import java.time.LocalDateTime;

import com.portmanagement.entity.VesselStatus;

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
public class VesselResponse {

    private Long id;

    private String name;

    private String imoNumber;

    private String vesselType;

    private String flagCountry;

    private Integer capacity;

    private VesselStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
