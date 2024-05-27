package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * The {@code VectorTest} class contains unit tests for the {@link Vector} class.
 * It covers various functionalities and edge cases of the Vector class methods.
 */
class VectorTest {

    final double DELTA = 0.0000001;

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
     * Tests the dotProduct method of the Vector class.
     * It verifies the correctness of the dot product calculation between vectors.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 4);
        Vector v1orthogonal = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(17, v1.dotProduct(v2), DELTA, "testDotProduct(): Incorrect dot product of the vectors");
        // =============== Boundary Values Tests ==================
        assertEquals(0, v1.dotProduct(v1orthogonal), DELTA, "ERROR: dotProduct() for orthogonal vectors is not zero");
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
        // Test subtract
        assertEquals(new Vector(0, 0, -1), v1.subtract(v2),
                "testAdd(): Vector subtraction did not work correctly");
        // =============== Boundary Values Tests ==================
        Vector v1Opposite = new Vector(-1, -2, -3);
        // Check addition of vector and its opposite
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite),
                "testAdd(): Expected addition of a vector and its opposite to throw IllegalArgumentException");
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
        // ============ Equivalence Partitions Tests ==============
        double scalar = 2.0;
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(new Vector(2, 4, 6), v1.scale(scalar), "testScale(): Incorrect scaling of the vector");
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
        assertTrue(isZero(u.length() - 1),
                "ERROR: the normalized vector is not a unit vector");
        // =============== Boundary Values Tests ==================
        // Check that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");
        // Check that the normalized vector is not opposite to the original one
        assertTrue(v.dotProduct(u) > 0, "ERROR: the normalized vector is opposite to the original one");
    }

}
