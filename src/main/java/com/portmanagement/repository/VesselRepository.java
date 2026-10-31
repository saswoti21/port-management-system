package com.portmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portmanagement.entity.Vessel;
import com.portmanagement.entity.VesselStatus;


@Repository
public interface VesselRepository extends JpaRepository<Vessel, Long>{
	
	Optional<Vessel> findByImoNumber(String imoNumber);
	
	boolean existsByImoNumber(String imoNumber);
	
	List<Vessel> findByNameContainingIgnoreCase(String name);
	
	List<Vessel> findByStatus(VesselStatus status);
}
