package org.example;

// This test class uses JUnit 5.
// You'll need to have the JUnit Jupiter API in your project dependencies.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals; // Added for scalar/length tests
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Test suite for the AbstractVec3 class.
 */
class AbstractVec3Test {

    private AbstractVec3 vec3Calculator;
    private final double DELTA = 0.0001; // Used for floating-point comparisons

    /**
     * Sets up a new instance of the class before each test.
     */
    @BeforeEach
    void setUp() {
        vec3Calculator = new AbstractVec3();
    }

    // --- Tests for Addition (Existing) ---

    @Test
    @DisplayName("Test addition with positive numbers")
    void testAdditionPositiveNumbers() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {4.0, 5.0, 6.0};
        double[] expected = {5.0, 7.0, 9.0};
        double[] result = vec3Calculator.addition(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test addition with negative numbers")
    void testAdditionNegativeNumbers() {
        double[] a = {-1.0, -2.0, -3.0};
        double[] b = {-4.0, -5.0, -6.0};
        double[] expected = {-5.0, -7.0, -9.0};
        double[] result = vec3Calculator.addition(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test addition with mixed positive and negative numbers")
    void testAdditionMixedNumbers() {
        double[] a = {1.5, -5.0, 0.0};
        double[] b = {2.5, 2.0, -3.0};
        double[] expected = {4.0, -3.0, -3.0};
        double[] result = vec3Calculator.addition(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test addition with zero vectors")
    void testAdditionWithZero() {
        double[] a = {0.0, 0.0, 0.0};
        double[] b = {10.0, 20.0, 30.0};
        double[] expected = {10.0, 20.0, 30.0};
        double[] result = vec3Calculator.addition(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test addition with input arrays of incorrect length")
    void testAdditionWithInvalidLength() {
        double[] a = {1.0, 2.0}; // Invalid length
        double[] b = {1.0, 2.0, 3.0};
        assertThrows(IllegalArgumentException.class, () -> vec3Calculator.addition(a, b));

        double[] c = {1.0, 2.0, 3.0};
        double[] d = {1.0, 2.0, 3.0, 4.0}; // Invalid length
        assertThrows(IllegalArgumentException.class, () -> vec3Calculator.addition(c, d));
    }

    // --- Tests for Subtraction (Existing) ---

    @Test
    @DisplayName("Test subtraction with positive numbers")
    void testSubtractionPositiveNumbers() {
        double[] a = {5.0, 7.0, 9.0};
        double[] b = {4.0, 5.0, 6.0};
        double[] expected = {1.0, 2.0, 3.0};
        double[] result = vec3Calculator.subtraction(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test subtraction with negative numbers")
    void testSubtractionNegativeNumbers() {
        double[] a = {-1.0, -2.0, -3.0};
        double[] b = {-4.0, -5.0, -6.0};
        double[] expected = {3.0, 3.0, 3.0}; // e.g., -1.0 - (-4.0) = 3.0
        double[] result = vec3Calculator.subtraction(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test subtraction with mixed positive and negative numbers")
    void testSubtractionMixedNumbers() {
        double[] a = {1.5, -5.0, 0.0};
        double[] b = {2.5, 2.0, -3.0};
        double[] expected = {-1.0, -7.0, 3.0}; // e.g., 0.0 - (-3.0) = 3.0
        double[] result = vec3Calculator.subtraction(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test subtraction with zero vectors")
    void testSubtractionWithZero() {
        double[] a = {10.0, 20.0, 30.0};
        double[] b = {0.0, 0.0, 0.0};
        double[] expected = {10.0, 20.0, 30.0};
        double[] result = vec3Calculator.subtraction(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test subtraction with input arrays of incorrect length")
    void testSubtractionWithInvalidLength() {
        double[] a = {1.0, 2.0}; // Invalid length
        double[] b = {1.0, 2.0, 3.0};
        assertThrows(IllegalArgumentException.class, () -> vec3Calculator.subtraction(a, b));
    }

    // --- Tests for Multiplication by Scalar (Existing) ---

    @Test
    @DisplayName("Test multiplication by a positive scalar")
    void testMultiplicationByPositiveScalar() {
        double[] a = {1.0, 2.0, 3.0};
        double x = 3.0;
        double[] expected = {3.0, 6.0, 9.0};
        double[] result = vec3Calculator.multiplicationByScalar(x, a);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test multiplication by a negative scalar")
    void testMultiplicationByNegativeScalar() {
        double[] a = {1.0, -2.0, 3.0};
        double x = -2.0;
        double[] expected = {-2.0, 4.0, -6.0};
        double[] result = vec3Calculator.multiplicationByScalar(x, a);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test multiplication by zero")
    void testMultiplicationByZero() {
        double[] a = {100.0, 200.0, 300.0};
        double x = 0.0;
        double[] expected = {0.0, 0.0, 0.0};
        double[] result = vec3Calculator.multiplicationByScalar(x, a);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test multiplication by one")
    void testMultiplicationByOne() {
        double[] a = {1.2, 3.4, 5.6};
        double x = 1.0;
        double[] expected = {1.2, 3.4, 5.6};
        double[] result = vec3Calculator.multiplicationByScalar(x, a);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test multiplication with input array of incorrect length")
    void testMultiplicationWithInvalidLength() {
        double[] a = {1.0, 2.0}; // Invalid length
        double x = 2.0;
        assertThrows(IllegalArgumentException.class, () -> vec3Calculator.multiplicationByScalar(x, a));
    }

    // --- Tests for Scalar Product (New) ---

    @Test
    @DisplayName("Test scalar product with positive numbers")
    void testScalarProductPositive() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {4.0, 5.0, 6.0};
        // 1*4 + 2*5 + 3*6 = 4 + 10 + 18 = 32
        double expected = 32.0;
        double result = vec3Calculator.scalarProduct(a, b);
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test scalar product with orthogonal vectors")
    void testScalarProductOrthogonal() {
        double[] a = {1.0, 0.0, 0.0};
        double[] b = {0.0, 1.0, 0.0};
        // 1*0 + 0*1 + 0*0 = 0
        double expected = 0.0;
        double result = vec3Calculator.scalarProduct(a, b);
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test scalar product with a zero vector")
    void testScalarProductWithZero() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {0.0, 0.0, 0.0};
        double expected = 0.0;
        double result = vec3Calculator.scalarProduct(a, b);
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test scalar product with mixed signs")
    void testScalarProductMixedSigns() {
        double[] a = {1.0, -2.0, 3.0};
        double[] b = {-4.0, 5.0, -6.0};
        // 1*(-4) + (-2)*5 + 3*(-6) = -4 - 10 - 18 = -32
        double expected = -32.0;
        double result = vec3Calculator.scalarProduct(a, b);
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test scalar product with invalid length")
    void testScalarProductWithInvalidLength() {
        double[] a = {1.0, 2.0};
        double[] b = {1.0, 2.0, 3.0};
        assertThrows(IllegalArgumentException.class, () -> vec3Calculator.scalarProduct(a, b));
    }

    // --- Tests for Vectorial Product (New) ---

    @Test
    @DisplayName("Test vectorial product of standard basis vectors (i x j = k)")
    void testVectorialProductStandardBasis() {
        double[] a = {1.0, 0.0, 0.0}; // i
        double[] b = {0.0, 1.0, 0.0}; // j
        double[] expected = {0.0, 0.0, 1.0}; // k
        double[] result = vec3Calculator.vectorialProduct(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test vectorial product of parallel vectors (a x a = 0)")
    void testVectorialProductParallel() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {2.0, 4.0, 6.0}; // b = 2a
        double[] expected = {0.0, 0.0, 0.0};
        double[] result = vec3Calculator.vectorialProduct(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test vectorial product general case")
    void testVectorialProductGeneral() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {4.0, 5.0, 6.0};
        // r[0] = 2*6 - 3*5 = 12 - 15 = -3
        // r[1] = 3*4 - 1*6 = 12 - 6 = 6
        // r[2] = 1*5 - 2*4 = 5 - 8 = -3
        double[] expected = {-3.0, 6.0, -3.0};
        double[] result = vec3Calculator.vectorialProduct(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test vectorial product with invalid length")
    void testVectorialProductWithInvalidLength() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {1.0, 2.0};
        assertThrows(IllegalArgumentException.class, () -> vec3Calculator.vectorialProduct(a, b));
    }

    // --- Tests for Schur Product (New) ---

    @Test
    @DisplayName("Test Schur product with positive numbers")
    void testSchurProductPositive() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {4.0, 5.0, 6.0};
        double[] expected = {4.0, 10.0, 18.0};
        double[] result = vec3Calculator.schurProduct(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test Schur product with mixed signs")
    void testSchurProductMixedSigns() {
        double[] a = {1.0, -2.0, 3.0};
        double[] b = {4.0, 5.0, -6.0};
        double[] expected = {4.0, -10.0, -18.0};
        double[] result = vec3Calculator.schurProduct(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test Schur product with zero vector")
    void testSchurProductWithZero() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {0.0, 0.0, 0.0};
        double[] expected = {0.0, 0.0, 0.0};
        double[] result = vec3Calculator.schurProduct(a, b);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test Schur product with invalid length")
    void testSchurProductWithInvalidLength() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {1.0, 2.0};
        assertThrows(IllegalArgumentException.class, () -> vec3Calculator.schurProduct(a, b));
    }

    // --- Tests for Length (New) ---

    @Test
    @DisplayName("Test length of zero vector")
    void testLengthZero() {
        double[] a = {0.0, 0.0, 0.0};
        double expected = 0.0;
        double result = vec3Calculator.length(a);
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test length of unit vector")
    void testLengthUnitVector() {
        double[] a = {0.0, 1.0, 0.0};
        double expected = 1.0;
        double result = vec3Calculator.length(a);
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test length of Pythagorean triple (3, 4, 12)")
    void testLengthPythagorean() {
        double[] a = {3.0, 4.0, 12.0};
        // sqrt(9 + 16 + 144) = sqrt(169) = 13
        double expected = 13.0;
        double result = vec3Calculator.length(a);
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test length of general vector")
    void testLengthGeneral() {
        double[] a = {1.0, 1.0, 1.0};
        // sqrt(1 + 1 + 1) = sqrt(3)
        double expected = Math.sqrt(3.0);
        double result = vec3Calculator.length(a);
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test length with invalid length")
    void testLengthWithInvalidLength() {
        double[] a = {1.0, 2.0};
        assertThrows(IllegalArgumentException.class, () -> vec3Calculator.length(a));
    }

    // --- Tests for Normalization (New) ---

    @Test
    @DisplayName("Test normalization of a simple vector")
    void testNormalizationSimple() {
        double[] a = {5.0, 0.0, 0.0};
        double[] expected = {1.0, 0.0, 0.0};
        double[] result = vec3Calculator.normalization(a);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test normalization of a general vector")
    void testNormalizationGeneral() {
        double[] a = {1.0, 1.0, 1.0};
        double len = Math.sqrt(3.0);
        double[] expected = {1.0 / len, 1.0 / len, 1.0 / len};
        double[] result = vec3Calculator.normalization(a);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test normalization of a zero vector (results in NaN)")
    void testNormalizationZeroVector() {
        double[] a = {0.0, 0.0, 0.0};
        // 1 / length(0) = 1 / 0.0 = Infinity
        // 0.0 * Infinity = NaN
        double[] expected = {Double.NaN, Double.NaN, Double.NaN};
        double[] result = vec3Calculator.normalization(a);
        assertArrayEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Test normalization with invalid length")
    void testNormalizationWithInvalidLength() {
        double[] a = {1.0, 2.0};
        assertThrows(IllegalArgumentException.class, () -> vec3Calculator.normalization(a));
    }
}