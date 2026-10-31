package com.portmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portmanagement.entity.Berth;
import com.portmanagement.entity.BerthStatus;

@Repository
public interface BerthRepository extends JpaRepository<Berth, Long>{
	
	Optional<Berth> findByBerthCode(String berthCode);

    boolean existsByBerthCode(String berthCode);

    List<Berth> findByBerthNameContainingIgnoreCase(String berthName);

    List<Berth> findByPortId(Long portId);

    List<Berth> findByStatus(BerthStatus status);
}
