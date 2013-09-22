package graphics.perpixel;
import graphics.PerPixel;

/**
 * a darken that works "correctly". Darken2 is just a fun algorithm that 
 * makes interesting patterns as a bug.
 * @author zkieda
 */
public class Darken implements PerPixel{
    public double dark_amount;
    public Darken(double dark_amount){this.dark_amount = dark_amount;}
    @Override
    public void process(int x, int y, byte[] src_pixel) {
        src_pixel[0] = (byte)((src_pixel[0] & 0xFF) * dark_amount);
        src_pixel[1] = (byte)((src_pixel[1] & 0xFF) * dark_amount);
        src_pixel[2] = (byte)((src_pixel[2] & 0xFF) * dark_amount);
    }
}
