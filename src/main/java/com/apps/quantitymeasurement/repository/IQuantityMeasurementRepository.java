package com.apps.quantitymeasurement.repository;

import java.util.List;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {
	
	//Saves a QuantityMeasurementEntity to the repository
	void save(QuantityMeasurementEntity entity);
	
	//Retrieves all QuantityMeasurementEntity instances from the repository
	//return a list of all QuantityMeasurementEntity instances
	List<QuantityMeasurementEntity> getAllMeasurements();
	
}
