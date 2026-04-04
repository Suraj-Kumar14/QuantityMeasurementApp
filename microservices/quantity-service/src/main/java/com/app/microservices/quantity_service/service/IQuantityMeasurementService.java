package com.app.microservices.quantity_service.service;

import java.util.List;

import com.app.microservices.quantity_service.dto.QuantityDTO;
import com.app.microservices.quantity_service.dto.QuantityMeasurementDTO;

public interface IQuantityMeasurementService {
	
	public QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, String username);
	
	public QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, String username);
	
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, String username);
	
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO, String username);
	
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, String username);
	
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO, String username);
	
	public QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, String username);
	
	List<QuantityMeasurementDTO> getHistoryByUsername(String username);
	
	List<QuantityMeasurementDTO> getOperationHistoryByUsername(String operation, String username);
	
	List<QuantityMeasurementDTO> getMeasurementByTypeAndUsername(String type, String username);
	
	Long getOperationCountByUsername(String operation, String username);
	
	List<QuantityMeasurementDTO> getErrorHistoryByUsername(String username);
}