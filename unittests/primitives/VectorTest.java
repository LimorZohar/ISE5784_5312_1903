package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {

    final double DELTA = 0.0000001;

    @Test
    void testVector() {
        assertThrows(IllegalArgumentException.class, () -> {new Vector(0, 0, 0);}, "Expected constructor to throw an IllegalArgumentException for zero vector");
    }

    @Test
    void testLengthSquared() {
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(14, v1.lengthSquared(), DELTA, "testLengthSquared(): Incorrect squared length of the vector");
    }

    @Test
    void testLength() {
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(Math.sqrt(14), v1.length(), DELTA, "testLength(): Incorrect length of the vector");
    }

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

    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 4);
        assertEquals(new Vector(2, 4, 7), v1.add(v2), "testAdd(): Vector addition did not work correctly");

        Vector v1Opposite = new Vector(-1, -2, -3);
        // Check addition of vector and its opposite
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite),
                "testAdd(): Expected addition of a vector and its opposite to throw IllegalArgumentException");
        // Test subtract
        assertEquals(new Vector(0, 0, -1), v1.subtract(v2),
                "testAdd(): Vector subtraction did not work correctly");

        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "testAdd(): Expected subtraction of a vector from itself to throw IllegalArgumentException");
    }

    @Test
    void testScale() {
        double scalar = 2.0;
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(new Vector(2, 4, 6), v1.scale(scalar), "testScale(): Incorrect scaling of the vector");
    }

    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 4);
        Vector v1parallel = new Vector(-2, -4, -6);
        Vector v1orthogonal = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v1orthogonal);

        assertEquals(new Vector(2, -1, 0), v1.crossProduct(v2), "testCrossProduct(): Incorrect cross product of the vectors");
        // parallel
        assertThrows(IllegalArgumentException.class, ()->v1.crossProduct(v1parallel),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");
        // length
        assertTrue(isZero(vr.length() - v1.length() * v1orthogonal.length()),
                "ERROR: crossProduct() wrong result length");
        // orthogonal
        assertTrue(isZero(vr.dotProduct(v1)) ||isZero(vr.dotProduct(v1orthogonal)),
                "ERROR: crossProduct() result is not orthogonal to its operands");
    }

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


