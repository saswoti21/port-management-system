package com.portmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portmanagement.entity.Port;

@Repository
public interface PortRepository extends JpaRepository<Port, Long>{
	boolean existsByPortCode(String portCode);
	List<Port> findByNameContainingIgnoreCaseOrPortCodeContainingIgnoreCaseOrCityContainingIgnoreCaseOrCountryContainingIgnoreCase(String name,String portCode,String city,String country);
}
