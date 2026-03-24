package com.app.quantitymeasurement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementServiceImplTest {

    private QuantityMeasurementServiceImpl service;
    private IQuantityMeasurementRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(IQuantityMeasurementRepository.class);
        service = new QuantityMeasurementServiceImpl(repository);
    }

    @Test
    void givenEqualLengthQuantities_WhenCompare_ThenShouldReturnTrue() {
        QuantityDTO dto1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

        boolean result = service.compare(dto1, dto2);

        assertTrue(result);
        verify(repository, atLeastOnce()).save(org.mockito.ArgumentMatchers.any());
    }

	/*
	 * @Test void
	 * givenLengthQuantityAndTargetUnit_WhenConvert_ThenShouldReturnConvertedValue()
	 * { QuantityDTO dto1 = new QuantityDTO(1.0, "FEET", "LengthUnit"); QuantityDTO
	 * target = new QuantityDTO(0.0, "INCHES", "LengthUnit");
	 * 
	 * QuantityDTO result = service.convert(dto1, target);
	 * 
	 * assertNotNull(result); assertEquals(12.0, result.getValue());
	 * assertEquals("INCHES", result.getUnit()); assertEquals("LengthUnit",
	 * result.getMeasurementType());
	 * 
	 * verify(repository, atLeastOnce()).save(org.mockito.ArgumentMatchers.any()); }
	 */

    @Test
    void givenTwoLengthQuantities_WhenAdd_ThenShouldReturnCorrectSum() {
        QuantityDTO dto1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(24.0, "INCHES", "LengthUnit");

        QuantityDTO result = service.add(dto1, dto2);

        assertNotNull(result);
        assertEquals(4.0, result.getValue());
        assertEquals("FEET", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());

        verify(repository, atLeastOnce()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void givenTwoLengthQuantitiesAndTargetUnit_WhenAdd_ThenShouldReturnCorrectSumInTargetUnit() {
        QuantityDTO dto1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(24.0, "INCHES", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");

        QuantityDTO result = service.add(dto1, dto2, target);

        assertNotNull(result);
        assertEquals(48.0, result.getValue());
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());

        verify(repository, atLeastOnce()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void givenTwoLengthQuantities_WhenSubtract_ThenShouldReturnCorrectDifference() {
        QuantityDTO dto1 = new QuantityDTO(5.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

        QuantityDTO result = service.subtract(dto1, dto2);

        assertNotNull(result);
        assertEquals(4.0, result.getValue());
        assertEquals("FEET", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());

        verify(repository, atLeastOnce()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void givenTwoLengthQuantitiesAndTargetUnit_WhenSubtract_ThenShouldReturnCorrectDifferenceInTargetUnit() {
        QuantityDTO dto1 = new QuantityDTO(5.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");

        QuantityDTO result = service.subtract(dto1, dto2, target);

        assertNotNull(result);
        assertEquals(48.0, result.getValue());
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());

        verify(repository, atLeastOnce()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void givenTwoLengthQuantities_WhenDivide_ThenShouldReturnCorrectResult() {
        QuantityDTO dto1 = new QuantityDTO(24.0, "INCHES", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

        double result = service.divide(dto1, dto2);

        assertNotNull(result);
       
    }

	/*
	 * @Test void givenNullDTO_WhenCompare_ThenShouldThrowException() {
	 * QuantityMeasurementException exception = assertThrows(
	 * QuantityMeasurementException.class, () -> service.compare(null, new
	 * QuantityDTO(12.0, "INCHES", "LengthUnit")) );
	 * 
	 * assertEquals("QuantityDTO objects can't be null!!!", exception.getMessage());
	 * }
	 */

    @Test
    void givenDifferentMeasurementTypes_WhenAdd_ThenShouldThrowCategoryMismatchException() {
        QuantityDTO dto1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(1.0, "LITRE", "VolumeUnit");

        assertThrows(
                QuantityMeasurementException.class,
                () -> service.add(dto1, dto2)
        );
    }

    @Test
    void givenInvalidUnit_WhenConvertToQuantityModel_ThenShouldThrowException() {
        QuantityDTO dto = new QuantityDTO(1.0, "INVALID_UNIT", "LengthUnit");

        assertThrows(IllegalArgumentException.class, () -> service.mapToModel(dto));
    }

	/*
	 * @Test void
	 * givenInvalidMeasurementType_WhenConvertToQuantityModel_ThenShouldThrowInvalidUnitMeasurementException
	 * () { QuantityDTO dto = new QuantityDTO(1.0, "FEET", "InvalidType");
	 * 
	 * assertThrows( QuantityMeasurementException.class, () ->
	 * service.mapToModel(dto) );
	 * 
	 * }
	 */

	/*
	 * @Test void
	 * givenInfiniteValue_WhenConvert_ThenShouldThrowQuantityMeasurementException()
	 * { QuantityDTO dto1 = new QuantityDTO(Double.POSITIVE_INFINITY, "FEET",
	 * "LengthUnit"); QuantityDTO target = new QuantityDTO(0.0, "INCHES",
	 * "LengthUnit");
	 * 
	 * QuantityMeasurementException exception = assertThrows(
	 * QuantityMeasurementException.class, () -> service.convert(dto1, target) );
	 * 
	 * assertEquals("Values must be finite numbers!!!", exception.getMessage()); }
	 */
}