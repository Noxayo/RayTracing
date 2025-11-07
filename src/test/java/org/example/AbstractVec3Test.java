package org.example; // Added package

// This test class uses JUnit 5.
// You'll need to have the JUnit Jupiter API in your project dependencies.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test suite for the AbstractVec3 class.
 */
class AbstractVec3Test {

    private AbstractVec3 vec3Calculator;

    /**
     * Sets up a new instance of the class before each test.
     */
    @BeforeEach
    void setUp() {
        vec3Calculator = new AbstractVec3();
    }

    @Test
    @DisplayName("Test addition with positive numbers")
    void testAdditionPositiveNumbers() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {4.0, 5.0, 6.0};
        double[] expected = {5.0, 7.0, 9.0};

        double[] result = vec3Calculator.addition(a, b);

        // Use assertArrayEquals for comparing arrays.
        // A delta (0.0001) is good practice for floating-point comparisons.
        assertArrayEquals(expected, result, 0.0001);
    }

    @Test
    @DisplayName("Test addition with negative numbers")
    void testAdditionNegativeNumbers() {
        double[] a = {-1.0, -2.0, -3.0};
        double[] b = {-4.0, -5.0, -6.0};
        double[] expected = {-5.0, -7.0, -9.0};

        double[] result = vec3Calculator.addition(a, b);

        assertArrayEquals(expected, result, 0.0001);
    }

    @Test
    @DisplayName("Test addition with mixed positive and negative numbers")
    void testAdditionMixedNumbers() {
        double[] a = {1.5, -5.0, 0.0};
        double[] b = {2.5, 2.0, -3.0};
        double[] expected = {4.0, -3.0, -3.0};

        double[] result = vec3Calculator.addition(a, b);

        assertArrayEquals(expected, result, 0.0001);
    }

    @Test
    @DisplayName("Test addition with zero vectors")
    void testAdditionWithZero() {
        double[] a = {0.0, 0.0, 0.0};
        double[] b = {10.0, 20.0, 30.0};
        double[] expected = {10.0, 20.0, 30.0};

        double[] result = vec3Calculator.addition(a, b);

        assertArrayEquals(expected, result, 0.0001);
    }

    @Test
    @DisplayName("Test addition with input arrays of incorrect length")
    void testAdditionWithInvalidLength() {
        double[] a = {1.0, 2.0}; // Invalid length
        double[] b = {1.0, 2.0, 3.0};

        // Test that the method throws an exception if the input is not length 3
        // I added this check to your original class.
        assertThrows(IllegalArgumentException.class, () -> {
            vec3Calculator.addition(a, b);
        });

        double[] c = {1.0, 2.0, 3.0};
        double[] d = {1.0, 2.0, 3.0, 4.0}; // Invalid length

        assertThrows(IllegalArgumentException.class, () -> {
            vec3Calculator.addition(c, d);
        });
    }

    // --- Tests for Subtraction ---

    @Test
    @DisplayName("Test subtraction with positive numbers")
    void testSubtractionPositiveNumbers() {
        double[] a = {5.0, 7.0, 9.0};
        double[] b = {4.0, 5.0, 6.0};
        double[] expected = {1.0, 2.0, 3.0};

        double[] result = vec3Calculator.subtraction(a, b);

        assertArrayEquals(expected, result, 0.0001);
    }

    @Test
    @DisplayName("Test subtraction with negative numbers")
    void testSubtractionNegativeNumbers() {
        double[] a = {-1.0, -2.0, -3.0};
        double[] b = {-4.0, -5.0, -6.0};
        double[] expected = {3.0, 3.0, 3.0}; // e.g., -1.0 - (-4.0) = 3.0

        double[] result = vec3Calculator.subtraction(a, b);

        assertArrayEquals(expected, result, 0.0001);
    }

    @Test
    @DisplayName("Test subtraction with mixed positive and negative numbers")
    void testSubtractionMixedNumbers() {
        double[] a = {1.5, -5.0, 0.0};
        double[] b = {2.5, 2.0, -3.0};
        double[] expected = {-1.0, -7.0, 3.0}; // e.g., 0.0 - (-3.0) = 3.0

        double[] result = vec3Calculator.subtraction(a, b);

        assertArrayEquals(expected, result, 0.0001);
    }

    @Test
    @DisplayName("Test subtraction with zero vectors")
    void testSubtractionWithZero() {
        double[] a = {10.0, 20.0, 30.0};
        double[] b = {0.0, 0.0, 0.0};
        double[] expected = {10.0, 20.0, 30.0};

        double[] result = vec3Calculator.subtraction(a, b);

        assertArrayEquals(expected, result, 0.0001);
    }

    @Test
    @DisplayName("Test subtraction with input arrays of incorrect length")
    void testSubtractionWithInvalidLength() {
        double[] a = {1.0, 2.0}; // Invalid length
        double[] b = {1.0, 2.0, 3.0};

        assertThrows(IllegalArgumentException.class, () -> {
            vec3Calculator.subtraction(a, b);
        });

        double[] c = {1.0, 2.0, 3.0};
        double[] d = {1.0, 2.0, 3.0, 4.0}; // Invalid length

        assertThrows(IllegalArgumentException.class, () -> {
            vec3Calculator.subtraction(c, d);
        });
    }
}