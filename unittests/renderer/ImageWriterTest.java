package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * Unit tests for ImageWriter class.
 */
class ImageWriterTest {

    /**
     * The width of the view plane in pixels.
     */
    int nX = 800;

    /**
     * The height of the view plane in pixels.
     */
    int nY = 500;

    /**
     * The color yellow for the yellow square on the view plane.
     */
    Color yellowColor = new Color(255d, 255d, 0d);

    /**
     * The color red for the net on the view plane.
     */
    Color redColor = new Color(255d, 0d, 0d);

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     * This method creates an image with a yellow background and a red grid.
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("yellow", nX, nY);
        //=== running on the view plane===//
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                //=== create the net ===//
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, redColor);
                } else {
                    imageWriter.writePixel(i, j, yellowColor);
                }
            }
        }
        imageWriter.writeToImage();
    }
}
