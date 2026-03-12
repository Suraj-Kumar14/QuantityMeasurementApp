package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        this.service = service;
    }

    public boolean performComparison(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return service.compare(thisQuantityDTO, thatQuantityDTO);
    }

    public QuantityDTO performConversion(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return service.convert(thisQuantityDTO, thatQuantityDTO);
    }

    public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return service.add(thisQuantityDTO, thatQuantityDTO);
    }

    public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        return service.add(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
    }

    public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return service.subtract(thisQuantityDTO, thatQuantityDTO);
    }

    public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        return service.subtract(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
    }

    public double performDivision(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return service.divide(thisQuantityDTO, thatQuantityDTO);
    }
}