package com.app.quantitymeasurement.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long>{

	//find all measurement by operation type
	List <QuantityMeasurementEntity> findByOperation(String operation);
	
	//find all measurement by measurement type
	List <QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);
	
	//find measurement created after specific date
	List <QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);
	
	//custom JPQL query for complex operations
	@Query("SELECT e FROM QuantityMeasurementEntity e WHERE e.operation = :operation AND e.isError = false")
	List<QuantityMeasurementEntity> findSuccessfulOperations(String operation);
	
	//Count successful operations
	long countByOperationAndIsErrorFalse(String operation);
	
	//find measurement with errors
	List <QuantityMeasurementEntity> findByIsErrorTrue();
	
}
