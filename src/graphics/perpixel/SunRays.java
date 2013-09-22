package graphics.perpixel;

import graphics.PerPixel;
import org.apache.commons.math3.util.FastMath;

/**
 * sunshine per pixel happiness!
 * 
 * @author zkieda
 */
public class SunRays implements PerPixel{
    
    //assert spokes > 2
    public int a, b, spokes;
    //a, b, is the (x, y) location of the center of the sunray
    
    //assert k >= D
    public float D, K, rot;
    //rot in radians.
    
    
    
    private float B_D;
    private float B;
    private float thetaMax;
    private float thetaMax_2;
    
    /**recalulates B_D. nice to do this in one call instead of having a million 
       helper functions that will recalulate B_D uneccecarily at every step.*/
    public final void recalculate(){
        thetaMax_2 = (float)(Math.PI/spokes);
        thetaMax = (float)(2*thetaMax_2);
        B_D = (float)(K*FastMath.sin(thetaMax_2)/(D - K*Math.cos(thetaMax_2)));
        B = B_D*D;
        B_D = -B_D;
//        System.out.println(B_D + " "+B);
    }
    public SunRays(final int a, final int b, final float D, final float K, final float rot, final int spokes){
        this.a = a; this.b = b; this.D = D; this.K = K; this.rot = rot; 
        this.spokes = spokes;
        recalculate();
    }
//    public static double atan2(double y, double x) {
//	double coeff_1 = Math.PI / 4d;
//	double coeff_2 = 3d * coeff_1;
//	double abs_y = Math.abs(y);
//	double angle;
//	if (x >= 0d) {
//		double r = (x - abs_y) / (x + abs_y);
//		angle = coeff_1 - coeff_1 * r;
//	} else {
//		double r = (x + abs_y) / (abs_y - x);
//		angle = coeff_2 - coeff_1 * r;
//	}
//	return y < 0d ? -angle : angle;
//    }
    private final static double two_pi = 2*Math.PI;
    @Override
    public void process(int x, int y, byte[] src_pixel) {
        x -= a; y = b-y;
        double radius = FastMath.hypot(x, y);
        if(radius > D) return;
        double theta = ((FastMath.atan2(y,x) + two_pi + rot)%two_pi);
        //assert theta > 0.
        theta %= thetaMax;
        if(theta > thetaMax_2) {theta = thetaMax-theta;}
//        assert theta < thetaMax && theta > -thetaMax;
        double yy = (radius * FastMath.sin(theta));
        double xx = (radius * Math.cos(theta));
        xx = B_D*xx+B;
        if(yy <= xx){
        src_pixel[0] = (byte)(255*(xx-yy)/xx*(D-radius)/D*(1 - theta*.5));
                //(byte)(255*(1 - theta)*(D-radius)/D);
        src_pixel[1] = 0;
        src_pixel[2] = 0;
        } 
//        else if(yy+1 >= xx){
//            src_pixel[0] = (byte)(255*(1 - theta)*(D-radius)/D);
//        }
//        src_pixel[3] *= radius;
//        x = radius*FastMath.sin(theta);
    }

}
