package graphics.perpixel;

import graphics.PerPixel;

/**
 * @author zkieda
 */
public class Darken2 implements PerPixel{
    public double dark_amount;
    public Darken2(double dark_amount){this.dark_amount = dark_amount;}
    @Override
    public void process(int x, int y, byte[] src_pixel) {
        src_pixel[0]*=dark_amount;
        src_pixel[1]*=dark_amount;
        src_pixel[2]*=dark_amount;
    }

}
