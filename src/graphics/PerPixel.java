package graphics;

/**
 * @author zkieda
 */
public interface PerPixel {
    /**
     * This is the 'final' processed part of graphics. Allows a person to 
     * apply nice gradients, etc. 
     * process a single pixel at position (x, y). The src_pixel is given in
     * values (r, g, b). 
     * 
     * The PPP process should be completed after the AWT processes have 
     * completed. 
     * 
     * The process should be disjoint, as we may run several pixel processes in
     * parallel to increase time efficiency.
     * 
     * @param src_pixel array of (r, g, b) components. You take these, process 
     * them, and fill them in. 
     */
    public void process(int x, int y, byte[] src_pixel);
}
