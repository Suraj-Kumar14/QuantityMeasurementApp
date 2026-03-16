package com.app.quantitymeasurement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.unit.LengthUnit;


public class QuantityMeasurementControllerTest {

    private QuantityMeasurementController controller;
    private IQuantityMeasurementService service;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(IQuantityMeasurementService.class);
        controller = new QuantityMeasurementController(service);
    }

    @Test
    void givenEqualLengthQuantities_WhenPerformComparison_ThenShouldReturnTrue() {
        QuantityDTO dto1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

        when(service.compare(dto1, dto2)).thenReturn(true);

        boolean result = controller.performComparison(dto1, dto2);

        assertTrue(result);
    }

    @Test
    void givenNonEqualLengthQuantities_WhenPerformComparison_ThenShouldReturnFalse() {
        QuantityDTO dto1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(10.0, "INCHES", "LengthUnit");

        when(service.compare(dto1, dto2)).thenReturn(false);

        boolean result = controller.performComparison(dto1, dto2);

        assertFalse(result);
    }

    @Test
    void givenLengthQuantityAndTargetUnit_WhenPerformConversion_ThenShouldReturnConvertedDTO() {
        QuantityDTO input = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");
        QuantityDTO expected = new QuantityDTO(12.0, LengthUnit.INCHES);

        when(service.convert(input, target)).thenReturn(expected);

        QuantityDTO result = controller.performConversion(input, target);

        assertEquals(12.0, result.getValue());
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

    @Test
    void givenTwoLengthQuantities_WhenPerformAddition_ThenShouldReturnSum() {
        QuantityDTO dto1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(24.0, "INCHES", "LengthUnit");
        QuantityDTO expected = new QuantityDTO(4.0, LengthUnit.FEET);

        when(service.add(dto1, dto2)).thenReturn(expected);

        QuantityDTO result = controller.performAddition(dto1, dto2);

        assertEquals(4.0, result.getValue());
        assertEquals("FEET", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

    @Test
    void givenTwoLengthQuantitiesAndTargetUnit_WhenPerformAddition_ThenShouldReturnSumInTargetUnit() {
        QuantityDTO dto1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(24.0, "INCHES", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");
        QuantityDTO expected = new QuantityDTO(48.0, LengthUnit.INCHES);

        when(service.add(dto1, dto2, target)).thenReturn(expected);

        QuantityDTO result = controller.performAddition(dto1, dto2, target);

        assertEquals(48.0, result.getValue());
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

    @Test
    void givenTwoLengthQuantities_WhenPerformSubtraction_ThenShouldReturnDifference() {
        QuantityDTO dto1 = new QuantityDTO(5.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");
        QuantityDTO expected = new QuantityDTO(4.0, LengthUnit.FEET);

        when(service.subtract(dto1, dto2)).thenReturn(expected);

        QuantityDTO result = controller.performSubtraction(dto1, dto2);

        assertEquals(4.0, result.getValue());
        assertEquals("FEET", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

    @Test
    void givenTwoLengthQuantitiesAndTargetUnit_WhenPerformSubtraction_ThenShouldReturnDifferenceInTargetUnit() {
        QuantityDTO dto1 = new QuantityDTO(5.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");
        QuantityDTO expected = new QuantityDTO(48.0, LengthUnit.INCHES);

        when(service.subtract(dto1, dto2, target)).thenReturn(expected);

        QuantityDTO result = controller.performSubtraction(dto1, dto2, target);

        assertEquals(48.0, result.getValue());
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

	/*
	 * @Test void
	 * givenTwoLengthQuantities_WhenPerformDivision_ThenShouldReturnDivisionResult()
	 * { QuantityDTO dto1 = new QuantityDTO(24.0, "INCHES", "LengthUnit");
	 * QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");
	 * 
	 * double result = controller.performDivision(dto1, dto2);
	 * 
	 * assertEquals(2.0, result);
	 * 
	 * }
	 */
}