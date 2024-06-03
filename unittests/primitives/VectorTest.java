package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * The {@code VectorTest} class contains unit tests for the {@link Vector} class.
 * It covers various functionalities and edge cases of the Vector class methods.
 */
class VectorTest {

    private double DELTA = 0.00001;

    /**
     * Tests the constructor of the Vector class.
     * It verifies that the constructor throws an IllegalArgumentException for a zero vector.
     */
    @Test
    void testVector() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Vector(0, 0, 0);
        }, "Expected constructor to throw an IllegalArgumentException for zero vector");
    }

    /**
     * Tests the lengthSquared method of the Vector class.
     * It checks if the squared length of a vector is computed correctly.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(14, v1.lengthSquared(), DELTA, "testLengthSquared(): Incorrect squared length of the vector");
    }

    /**
     * Tests the length method of the Vector class.
     * It checks if the length of a vector is computed correctly.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(Math.sqrt(14), v1.length(), DELTA, "testLength(): Incorrect length of the vector");
    }

    /**
     * Tests the add and subtract methods of the Vector class.
     * It verifies the correctness of vector addition and subtraction operations.
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 4);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(2, 4, 7), v1.add(v2),
                "testAdd(): Vector addition did not work correctly");
        // =============== Boundary Values Tests ==================
        // Check addition of vector and its opposite
        assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(-1, -2, -3)),
                "testAdd(): Expected addition of a vector and its opposite to throw IllegalArgumentException");
    }

    /**
     * Tests the dotProduct method of the Vector class.
     * It verifies the correctness of the dot product calculation between vectors.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 4);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(17, v1.dotProduct(v2), DELTA,
                "testDotProduct(): Incorrect dot product of the vectors");
        // =============== Boundary Values Tests ==================
        assertEquals(0, v1.dotProduct(new Vector(0, 3, -2)), DELTA,
                "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Tests the subtract method of the Vector class.
     * It verifies the correctness of vector subtraction operations.
     */
    @Test
    void testSubtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 4);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(0, 0, 1), v2.subtract(v1),
                "testSubtract: Subtraction of vectors did not produce the expected result");
        // =============== Boundary Values Tests ==================
        // Check subtract of vector and itself
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "testAdd(): Expected subtraction of a vector from itself to throw IllegalArgumentException");
    }

    /**
     * Tests the scale method of the Vector class.
     * It verifies that vector scaling is performed correctly.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============double scalar = 2.0;
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(new Vector(2, 4, 6), v1.scale(2.0),
                "testScale(): Incorrect scaling of the vector");
        // =============== Boundary Values Tests ==================
        // Test scaling with zero should throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0.0),
                "testScale(): Scaling by zero should throw IllegalArgumentException");
    }

    /**
     * Tests the crossProduct method of the Vector class.
     * It checks if the cross product of vectors is computed correctly.
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 4);
        Vector v1parallel = new Vector(-2, -4, -6);
        Vector v1orthogonal = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v1orthogonal);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(2, -1, 0), v1.crossProduct(v2),
                "testCrossProduct(): Incorrect cross product of the vectors");
        // =============== Boundary Values Tests ==================
        // parallel
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v1parallel),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");
        // length
        assertTrue(isZero(vr.length() - v1.length() * v1orthogonal.length()),
                "ERROR: crossProduct() wrong result length");
        // orthogonal
        assertTrue(isZero(vr.dotProduct(v1)) || isZero(vr.dotProduct(v1orthogonal)),
                "ERROR: crossProduct() result is not orthogonal to its operands");
    }

    /**
     * Tests the normalize method of the Vector class.
     * It checks if the normalization of a vector is performed correctly.
     * The test covers equivalence partitions and boundary values.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        // Check if the normalized vector is a unit vector
        assertEquals(1.0, u.length(), DELTA,
                "ERROR: the normalized vector is not a unit vector");
        // Check that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");
        // Check that the normalized vector is not opposite to the original one
        assertTrue(v.dotProduct(u) > 0,
                "ERROR: the normalized vector is opposite to the original one");
    }

}
