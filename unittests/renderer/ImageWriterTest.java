package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ImageWriter class.
 */
class ImageWriterTest {

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     * This test creates an image with a yellow background.
     */
    @Test
    void writeToImage() {
        ImageWriter imageWriter = new ImageWriter("yellow", 800, 500);
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, new Color(java.awt.Color.YELLOW));
            }
        }
        imageWriter.writeToImage();
    }
}
