package graphics.perpixel;

import graphics.PerPixel;
import util.ScreenInfo;

/**
 * currently, the paint goes from transparent inner circle to a black outer.
 * 
 * @author zkieda
 */
public class CircleGradient implements PerPixel{
    private int x_pos, y_pos;
    private double largest_len;
    private double factor = 1;
    public double getFactor(){
        return factor;
    }
    public void setFactor(double factor){
        this.factor = factor;
    }
    private void calculate(){
        int l_w = x_pos > ScreenInfo.SCREEN_SIZE.width>>1?x_pos:ScreenInfo.SCREEN_SIZE.width - x_pos;
        int l_h = y_pos > ScreenInfo.SCREEN_SIZE.height>>1?y_pos:(ScreenInfo.SCREEN_SIZE.height - y_pos);
        largest_len = Math.sqrt(l_h*l_h + l_w*l_w);
    }
    public CircleGradient(int x_center, int y_center){
        this.x_pos = x_center;
        this.y_pos = y_center;
        calculate();
    }
    public boolean invert = false;
    //apply a simple per pixel gradient.
    @Override public void process(int x, int y, byte[] src_pixel) {
        x -= x_pos;//distance to middle x
        y -= y_pos;//distance to middle y

        //|x^2 + y^2| = distance to middle from (x,y)

        
        final double influence;
//        if(invert) influence= (Math.sqrt(x*x + y*y)/largest_len)*factor + (1- factor);
//        else 
            influence= (1-Math.sqrt(x*x + y*y)/largest_len)*factor + (1- factor);
        //between 0 and 1. Is one towards the middle, 0 towards the 
        //edges.
        
        if(influence >= 0 && influence <= 1)
                //comment this out to enable trippy shit. Currently, if there's 
                //a smaller radius than can cover the screen it just goes dark.
        for(int i = 0; i < src_pixel.length;i++)
            src_pixel[i] = (byte)(((src_pixel[i])&0xFF)*influence);
                //we multiply by its influence, or, make it darker 
                //as we reach the edge of the screen and lighter in
                //the middle.
        else for(int i = 0; i < src_pixel.length;i++) src_pixel[i] = 0;
    }
    //sets the x and y coordinates, and recalculates if necessary.
    public void setXY(int x_center, int y_center, boolean recalculate){
        this.x_pos = x_center;
        this.y_pos = y_center;
        if(recalculate) calculate();
    }
    public void setXY(int x_center, int y_center, double radius){
        this.x_pos = x_center;
        this.y_pos = y_center;
        this.largest_len = radius;
    }
    public void setRadius(double radius){
        this.largest_len = radius;
    }
    public int getX(){
        return x_pos;
    }
    public int getY(){
        return y_pos;
    }
    public double getRadius(){
        return largest_len;
    }
}
