package renderer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * PixelManager is a helper class for managing pixel allocation in multi-threaded rendering.
 * It ensures that each thread processes a unique pixel and keeps track of the rendering progress.
 * A Camera uses one PixelManager object and several Pixel objects - one in each thread.
 */
class PixelManager {
    /**
     * Immutable record for an object containing the allocated pixel with its row and column numbers.
     *
     * @param col The column number of the pixel.
     * @param row The row number of the pixel.
     */

    record Pixel(int col, int row) {

    }

    /**
     * The maximum number of rows of pixels in the image
     */
    private final int maxRows;
    /**
     * The maximum number of columns of pixels in the image
     */
    private final int maxCols;
    /**
     * The total number of pixels in the generated image
     */
    private final long totalPixels;

    /**
     * The current row of pixels being processed
     */
    private final AtomicInteger currentRow = new AtomicInteger(0);
    /**
     * The current column of pixels being processed
     */
    private final AtomicInteger currentCol = new AtomicInteger(-1);
    /**
     * The number of pixels that have been processed
     */
    private final AtomicLong processedPixels = new AtomicLong(0);

    /**
     * The mutex object for synchronizing the next pixel allocation between threads
     */
    private final Object nextPixelLock = new Object();

    /**
     * Initializes the PixelManager with the given number of rows and columns.
     *
     * @param maxRows The number of rows of pixels in the image.
     * @param maxCols The number of columns of pixels in the image.
     */
    PixelManager(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.totalPixels = (long) maxRows * maxCols;
    }

    /**
     * Provides the next available pixel for rendering in a thread-safe manner.
     * This function is a critical section for all the threads, and the PixelManager data is shared between threads.
     * It ensures that each thread processes a unique pixel.
     *
     * @return The next available Pixel, or null if there are no more pixels to process.
     */
    Pixel nextPixel() {
        synchronized (nextPixelLock) {
            if (currentRow.get() == maxRows) return null;

            int col = currentCol.incrementAndGet();
            if (col < maxCols) return new Pixel(col, currentRow.get());

            currentCol.set(0);
            if (currentRow.incrementAndGet() < maxRows)
                return new Pixel(0, currentRow.get());
        }
        return null;
    }

    /**
     * Marks a pixel as processed by updating the number of processed pixels.
     * This method is used to track the progress of the rendering process.
     */
    void pixelDone() {
        processedPixels.incrementAndGet();
    }
}
