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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(
    name = "berths",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_berth_code",
            columnNames = "berth_code"
        )
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Berth {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
	private String berthName;
	
	@NotBlank
    @Size(max = 20)
    @Column(name = "berth_code", nullable = false, length = 20)
	private String berthCode;
	
	@NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
	private String berthType;
	
	@NotNull
    @DecimalMin(value = "0.1")
    @Column(nullable = false)
	private Double length;
	
	@NotNull
    @DecimalMin(value = "0.1")
    @Column(nullable = false)
	private Double depth;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
	private BerthStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "port_id", nullable = false)
	private Port port;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		createdAt=LocalDateTime.now();
		updatedAt=LocalDateTime.now();
		
		if(status==null) {
			status = BerthStatus.ACTIVE;
		}
	}
	
	protected void onUpdated() {
		updatedAt=LocalDateTime.now();
	}
}
