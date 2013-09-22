package graphics.perpixel;

import graphics.PerPixel;
import java.util.Random;

/**
 * strictly dependant speculation. Meaning, we should only call this within
 * another state;
 * @author zkieda
 */
public class Speculate implements PerPixel{
    private static Random generator = new Random();
    
    /**
     * makes the screen appear shiny or some shit. (i.e. apply random values
     * that are slightly different than our background pixel type as a nice 
     * touch). Ideally, we would want to have a gaussian- like (or poisson) 
     * distribution for the probability for the difference in hue, but doing 
     * so is too cost inefficient (or because I'm an efficiency nut). 
     * 
     * 
     * 
     * @param difference what's the maximum deviation that we are willing to 
     * go? 
     * when @code{difference = 0}, we do not change values at all.
     * when @code{difference = 1}, we are willing to go to the exact opposite 
     * color.
     * 
     * Let <i>c</i> be a component in [0, 255]
     * Let <i>c_src</i> be the source component of the pixel in question. 
     * 
     * We then modify to a new component color <i>c_out</i>, such that 
     * 
        component new = 
     */
    private double diff;
    public Speculate(double difference){
        this.diff = difference;
    }
    public boolean use_gaussian = true;
    @Override public void process(int x, int y, byte[] src_pixel) {
        double rand = generator.nextDouble();
        double d1;
        if(use_gaussian){
            double d2;
            for(int i = 0; i < src_pixel.length; i++){
                d1 = ((src_pixel[i]&0xFF)/255.0);
                if((d1 != 0 && generator.nextDouble() < d1) || d1 == 1){
                    d1 *= diff;
                    d2 = -StrictMath.sqrt(
                            -Math.log(
                                (rand*(1-org.apache.commons.math3.util.FastMath.exp(-(d1*d1)/2)) + 1)/2
                            ));
                    src_pixel[i] = (byte)((int)(src_pixel[i] + d2*d1)&0xFF);
                } else{
                    d2 = (1.0 - d1)*diff;
                    d1 = StrictMath.sqrt(
                            -Math.log(
                                (rand*(1-org.apache.commons.math3.util.FastMath.exp(-(d2*d2)/2)) + 1)/2
                            ));
                    src_pixel[i] = (byte)((int)(src_pixel[i] + d1*d2)&0xFF);
                }
            }
        } else {
            for(int i = 0; i < src_pixel.length; i++){
                int i1 = src_pixel[i]&0xFF;
                d1 = ((i1)/255.0);
                if(generator.nextDouble() < d1)
                    src_pixel[i] = (byte)(i1*(1 - rand*diff));
                else
                    src_pixel[i] = (byte)((255 - i1)*diff*rand + i1);
            }
        }
    }
    public void setDiff(double diff){
        this.diff = diff;
    }
}
