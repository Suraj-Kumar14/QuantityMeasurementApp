package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityModel;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.unit.IMeasurable;
import com.apps.quantitymeasurement.unit.LengthUnit;
import com.apps.quantitymeasurement.unit.TemperatureUnit;
import com.apps.quantitymeasurement.unit.VolumeUnit;
import com.apps.quantitymeasurement.unit.WeightUnit;

public class QuantityMeasurementAppTest {

    private final QuantityMeasurementController controller = QuantityMeasurementApp.getInstance().controller;

    // ----------------------------------------------------
    // DTO TESTS
    // ----------------------------------------------------

    @Test
    public void testQuantityDTO_StringConstructor() {
        QuantityDTO quantity = new QuantityDTO(1.0, "FEET", "LENGTH");

        assertEquals(1.0, quantity.getValue());
        assertEquals("FEET", quantity.getUnit());
        assertEquals("LENGTH", quantity.getMeasurementType());
    }

    @Test
    void testQuantityDTO_Constructor_WithIMeasurable() {
        QuantityDTO dto = new QuantityDTO(1.0, LengthUnit.FEET);

        assertEquals(1.0, dto.getValue());
        assertEquals("FEET", dto.getUnit());
        assertEquals("LengthUnit", dto.getMeasurementType());
    }

    @Test
    void testQuantityDTO_Constructor_WithStrings() {
        QuantityDTO dto = new QuantityDTO(2.0, "GRAM", "WeightUnit");

        assertEquals(2.0, dto.getValue());
        assertEquals("GRAM", dto.getUnit());
        assertEquals("WeightUnit", dto.getMeasurementType());
    }

    @Test
    void testQuantityDTO_ToString() {
        QuantityDTO dto = new QuantityDTO(1.0, "LITRE", "VolumeUnit");
        String text = dto.toString();

        assertTrue(text.contains("1.0"));
        assertTrue(text.contains("LITRE"));
        assertTrue(text.contains("VolumeUnit"));
    }

    // ----------------------------------------------------
    // MODEL TESTS
    // ----------------------------------------------------

    @Test
    void testQuantityModel_Construction() {
        QuantityModel<IMeasurable> model = new QuantityModel<>(5.0, LengthUnit.INCHES);

        assertEquals(5.0, model.getValue());
        assertEquals(LengthUnit.INCHES, model.getUnit());
    }

    @Test
    void testQuantityModel_ToString() {
        QuantityModel<IMeasurable> model = new QuantityModel<>(10.0, WeightUnit.KILOGRAM);
        String text = model.toString();

        assertTrue(text.contains("10.0"));
        assertTrue(text.contains("KILOGRAM"));
    }

    // ----------------------------------------------------
    // SERVICE - COMPARE
    // ----------------------------------------------------

    @Test
    void testService_CompareEquality_SameUnit_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(1.0, LengthUnit.FEET);

        boolean result = controller.performComparison(q1, q2);

        assertTrue(result);
    }

    @Test
    void testService_CompareEquality_DifferentUnit_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, LengthUnit.INCHES);

        boolean result = controller.performComparison(q1, q2);

        assertTrue(result);
    }

    @Test
    void testService_CompareEquality_CrossCategory_Error() {
        QuantityDTO q1 = new QuantityDTO(1.0, LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(1.0, VolumeUnit.LITRE);

        assertThrows(QuantityMeasurementException.class,
                () -> controller.performComparison(q1, q2));
    }

    // ----------------------------------------------------
    // SERVICE - CONVERT
    // ----------------------------------------------------

    @Test
    void testService_Convert_Success() {
        QuantityDTO source = new QuantityDTO(1.0, LengthUnit.FEET);
        QuantityDTO target = new QuantityDTO(0.0, LengthUnit.INCHES);

        QuantityDTO result = controller.performConversion(source, target);

        assertEquals(12.0, result.getValue(), 0.01);
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

    // ----------------------------------------------------
    // SERVICE - ADD
    // ----------------------------------------------------

    @Test
    void testService_Add_DefaultTargetUnit_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, LengthUnit.INCHES);

        QuantityDTO result = controller.performAddition(q1, q2);

        assertEquals(2.0, result.getValue(), 0.01);
        assertEquals("FEET", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

    @Test
    void testService_Add_WithTargetUnit_Success() {
        QuantityDTO q1 = new QuantityDTO(1.0, LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, LengthUnit.INCHES);
        QuantityDTO target = new QuantityDTO(0.0, LengthUnit.INCHES);

        QuantityDTO result = controller.performAddition(q1, q2, target);

        assertEquals(24.0, result.getValue(), 0.01);
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

    @Test
    void testService_Add_CrossCategory_Error() {
        QuantityDTO q1 = new QuantityDTO(1.0, LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(1.0, WeightUnit.KILOGRAM);

        assertThrows(QuantityMeasurementException.class,
                () -> controller.performAddition(q1, q2));
    }

    // ----------------------------------------------------
    // SERVICE - SUBTRACT
    // ----------------------------------------------------

    @Test
    void testService_Subtract_DefaultTargetUnit_Success() {
        QuantityDTO q1 = new QuantityDTO(5.0, WeightUnit.KILOGRAM);
        QuantityDTO q2 = new QuantityDTO(2000.0, WeightUnit.GRAM);

        QuantityDTO result = controller.performSubtraction(q1, q2);

        assertEquals(3.0, result.getValue(), 0.01);
        assertEquals("KILOGRAM", result.getUnit());
        assertEquals("WeightUnit", result.getMeasurementType());
    }

    @Test
    void testService_Subtract_WithTargetUnit_Success() {
        QuantityDTO q1 = new QuantityDTO(5.0, WeightUnit.KILOGRAM);
        QuantityDTO q2 = new QuantityDTO(2000.0, WeightUnit.GRAM);
        QuantityDTO target = new QuantityDTO(0.0, WeightUnit.GRAM);

        QuantityDTO result = controller.performSubtraction(q1, q2, target);

        assertEquals(3000.0, result.getValue(), 0.01);
        assertEquals("GRAM", result.getUnit());
        assertEquals("WeightUnit", result.getMeasurementType());
    }

    // ----------------------------------------------------
    // SERVICE - DIVIDE
    // ----------------------------------------------------

    @Test
    void testService_Divide_Success() {
        QuantityDTO q1 = new QuantityDTO(2.0, VolumeUnit.LITRE);
        QuantityDTO q2 = new QuantityDTO(1000.0, VolumeUnit.MILLILITRE);

        double result = controller.performDivision(q1, q2);

        assertEquals(2.0, result, 0.01);
        assertEquals("LITRE", q1.getUnit());
        assertEquals("VolumeUnit", q1.getMeasurementType());
    }

    @Test
    void testService_Divide_ByZero_Error() {
        QuantityDTO q1 = new QuantityDTO(10.0, LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(0.0, LengthUnit.FEET);

        assertThrows(ArithmeticException.class,
                () -> controller.performDivision(q1, q2));
    }

    // ----------------------------------------------------
    // SERVICE - VALIDATION
    // ----------------------------------------------------

    @Test
    void testService_NullEntity_Rejection() {
        QuantityDTO q = new QuantityDTO(1.0, LengthUnit.FEET);

        assertThrows(QuantityMeasurementException.class,
                () -> controller.performAddition(null, q));
    }

    @Test
    void testService_ValidationConsistency() {
        QuantityDTO q = new QuantityDTO(1.0, LengthUnit.FEET);

        assertThrows(QuantityMeasurementException.class,
                () -> controller.performAddition(null, q));
        assertThrows(QuantityMeasurementException.class,
                () -> controller.performAddition(null, q));
        assertThrows(QuantityMeasurementException.class,
                () -> controller.performAddition(null, q));
    }

    // ----------------------------------------------------
    // SERVICE - ALL CURRENT MEASUREMENT CATEGORIES
    // ----------------------------------------------------

    @Test
    void testService_AllMeasurementCategories_CurrentlySupported() {
        assertTrue(controller.performComparison(
                new QuantityDTO(1.0, LengthUnit.FEET),
                new QuantityDTO(12.0, LengthUnit.INCHES)));

        assertTrue(controller.performComparison(
                new QuantityDTO(1.0, WeightUnit.KILOGRAM),
                new QuantityDTO(1000.0, WeightUnit.GRAM)));

        assertTrue(controller.performComparison(
                new QuantityDTO(1.0, VolumeUnit.LITRE),
                new QuantityDTO(1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testService_Temperature_Comparison_Supported() {
        QuantityDTO t1 = new QuantityDTO(0.0, TemperatureUnit.CELSIUS);
        QuantityDTO t2 = new QuantityDTO(32.0, TemperatureUnit.FAHRENHEIT);

        boolean result = controller.performComparison(t1, t2);

        assertTrue(result);
    }
}