package com.app.quantitymeasurement.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QuantityMeasurementEntityTest {

    @Test
    void givenEntityWithStringResult_WhenCreated_ThenFieldsShouldBeInitializedCorrectly() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                1.0, "FEET", "LengthUnit",
                12.0, "INCHES", "LengthUnit",
                "COMPARISON",
                "true"
        );

        assertEquals(1.0, entity.thisValue);
        assertEquals("FEET", entity.thisUnit);
        assertEquals("LengthUnit", entity.thisMeasurementType);

        assertEquals(12.0, entity.thatValue);
        assertEquals("INCHES", entity.thatUnit);
        assertEquals("LengthUnit", entity.thatMeasurementType);

        assertEquals("COMPARISON", entity.operation);
        assertEquals("true", entity.resultString);
        assertFalse(entity.isError);
    }

    @Test
    void givenEntityWithNumericResult_WhenCreated_ThenFieldsShouldBeInitializedCorrectly() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                2.0, "FEET", "LengthUnit",
                24.0, "INCHES", "LengthUnit",
                "ADD",
                4.0, "FEET", "LengthUnit"
        );

        assertEquals(2.0, entity.thisValue);
        assertEquals("FEET", entity.thisUnit);
        assertEquals("LengthUnit", entity.thisMeasurementType);

        assertEquals(24.0, entity.thatValue);
        assertEquals("INCHES", entity.thatUnit);
        assertEquals("LengthUnit", entity.thatMeasurementType);

        assertEquals("ADD", entity.operation);
        assertEquals(4.0, entity.resultValue);
        assertEquals("FEET", entity.resultUnit);
        assertEquals("LengthUnit", entity.resultMeasurementType);
        assertFalse(entity.isError);
    }

    @Test
    void givenEntityWithFullConstructor_WhenCreated_ThenAllFieldsShouldBeInitializedCorrectly() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                5.0, "FEET", "LengthUnit",
                12.0, "INCHES", "LengthUnit",
                "SUBTRACT",
                4.0, "FEET", "LengthUnit",
                "result saved",
                false,
                null
        );

        assertEquals(5.0, entity.thisValue);
        assertEquals("FEET", entity.thisUnit);
        assertEquals("LengthUnit", entity.thisMeasurementType);

        assertEquals(12.0, entity.thatValue);
        assertEquals("INCHES", entity.thatUnit);
        assertEquals("LengthUnit", entity.thatMeasurementType);

        assertEquals("SUBTRACT", entity.operation);
        assertEquals(4.0, entity.resultValue);
        assertEquals("FEET", entity.resultUnit);
        assertEquals("LengthUnit", entity.resultMeasurementType);
        assertEquals("result saved", entity.resultString);
        assertFalse(entity.isError);
        assertEquals(null, entity.errorMessage);
    }

    @Test
    void givenEntityWithOnlyResultValue_WhenCreated_ThenFieldsShouldBeInitializedCorrectly() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                24.0, "INCHES", "LengthUnit",
                12.0, "INCHES", "LengthUnit",
                "DIVIDE",
                2.0
        );

        assertEquals(24.0, entity.thisValue);
        assertEquals("INCHES", entity.thisUnit);
        assertEquals("LengthUnit", entity.thisMeasurementType);

        assertEquals(12.0, entity.thatValue);
        assertEquals("INCHES", entity.thatUnit);
        assertEquals("LengthUnit", entity.thatMeasurementType);

        assertEquals("DIVIDE", entity.operation);
        assertEquals(2.0, entity.resultValue);
        assertFalse(entity.isError);
    }

    @Test
    void givenEntityWithError_WhenCreated_ThenErrorFieldsShouldBeInitializedCorrectly() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                1.0, "FEET", "LengthUnit",
                1.0, "LITRE", "VolumeUnit",
                "ADD",
                "Operation not possible on two different measurement types!!!",
                true
        );

        assertEquals(1.0, entity.thisValue);
        assertEquals("FEET", entity.thisUnit);
        assertEquals("LengthUnit", entity.thisMeasurementType);

        assertEquals(1.0, entity.thatValue);
        assertEquals("LITRE", entity.thatUnit);
        assertEquals("VolumeUnit", entity.thatMeasurementType);

        assertEquals("ADD", entity.operation);
        assertEquals("Operation not possible on two different measurement types!!!", entity.errorMessage);
        assertTrue(entity.isError);
    }

    @Test
    void givenTwoEqualEntities_WhenCompared_ThenShouldReturnTrue() {
        QuantityMeasurementEntity entity1 = new QuantityMeasurementEntity(
                2.0, "FEET", "LengthUnit",
                24.0, "INCHES", "LengthUnit",
                "ADD",
                4.0, "FEET", "LengthUnit"
        );

        QuantityMeasurementEntity entity2 = new QuantityMeasurementEntity(
                2.0, "FEET", "LengthUnit",
                24.0, "INCHES", "LengthUnit",
                "ADD",
                4.0, "FEET", "LengthUnit"
        );

        assertTrue(entity1.equals(entity2));
    }

    @Test
    void givenTwoDifferentEntities_WhenCompared_ThenShouldReturnFalse() {
        QuantityMeasurementEntity entity1 = new QuantityMeasurementEntity(
                2.0, "FEET", "LengthUnit",
                24.0, "INCHES", "LengthUnit",
                "ADD",
                4.0, "FEET", "LengthUnit"
        );

        QuantityMeasurementEntity entity2 = new QuantityMeasurementEntity(
                5.0, "FEET", "LengthUnit",
                12.0, "INCHES", "LengthUnit",
                "SUBTRACT",
                4.0, "FEET", "LengthUnit"
        );

        assertFalse(entity1.equals(entity2));
    }

    @Test
    void givenEntity_WhenToStringCalled_ThenShouldReturnNonNullFormattedText() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                2.0, "FEET", "LengthUnit",
                24.0, "INCHES", "LengthUnit",
                "ADD",
                4.0, "FEET", "LengthUnit"
        );

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("This Value"));
        assertTrue(result.contains("That Value"));
        assertTrue(result.contains("Operation"));
        assertTrue(result.contains("Result Value"));
    }
}