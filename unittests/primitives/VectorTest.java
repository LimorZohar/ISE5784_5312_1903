package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(17, v1.dotProduct(v2), DELTA, "testDotProduct(): Incorrect dot product of the vectors");
    }

    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 4);
        assertEquals(new Vector(2, 4, 7), v1.add(v2), "testAdd(): Incorrect addition of vectors");
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
        assertEquals(new Vector(2, -1, 0), v1.crossProduct(v2), "testCrossProduct(): Incorrect cross product of the vectors");
    }



    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v1 = new Vector(0,3,4);
        Vector n = v1.normalize();
        //
        assertEquals(1.0, n.lengthSquared(), DELTA,"wrong normalized vector length");
        assertThrows(IllegalArgumentException.class,()->v1.crossProduct(n),"normalized vector is not in the same direction");
        assertEquals(new Vector (0,0.6,0.8), n, "wrong normalized vector");

        Vector unitVector = new Vector(1,0,0);
        Vector nUnitVector = unitVector.normalize();
        assertEquals(unitVector, nUnitVector, "normalized vector is not the same as the original unit vector");

    }

}


