package primitives;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @org.junit.jupiter.api.Test
    void testLengthSquared() {
        Vector v1 = new Vector(1,2,3);
        assertEquals(14,v1.lengthSquared());
    }

    @org.junit.jupiter.api.Test
    void testLength() {
        Vector v1 = new Vector(1,2,3);
        assertEquals(Math.sqrt(14),v1.length());
    }

    @org.junit.jupiter.api.Test
    void testDotProduct() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,2,4);
        assertEquals(17,v1.dotProduct(v2));

    }

    @org.junit.jupiter.api.Test
    void testAdd() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,2,4);
        assertEquals(new Vector(2,4,7),v1.add(v2));
    }

    @org.junit.jupiter.api.Test
    void testScale() {
    }

    @org.junit.jupiter.api.Test
    void testCrossProduct() {
        Vector v1 = new Vector(1,2,3);
    }

    @org.junit.jupiter.api.Test
    void testNormalize() {
    }
}