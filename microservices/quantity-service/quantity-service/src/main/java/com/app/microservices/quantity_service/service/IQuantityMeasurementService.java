package com.app.microservices.quantity_service.service;

import java.util.List;

import com.app.microservices.quantity_service.dto.QuantityDTO;
import com.app.microservices.quantity_service.dto.QuantityMeasurementDTO;
import com.app.microservices.quantity_service.entity.QuantityMeasurementEntity;


public interface IQuantityMeasurementService {
	
	public QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,QuantityDTO targetUnitDTO );
	
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,QuantityDTO targetUnitDTO );
	
	public QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	List<QuantityMeasurementDTO> getOpeartionHistory(String operation);
	
	List<QuantityMeasurementDTO> getMeasurementByType(String type);
	
	Long getOperationCount(String operation);
	
	List<QuantityMeasurementDTO> getErrorHistory();	
	
	List<QuantityMeasurementEntity>getAllHistory();
	
}