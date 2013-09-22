package graphics.anim;

import drivers.Main;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import static graphics.AWT.*;
import gui.MainFrame;
import java.awt.Color;
import java.util.Random;
import state.State;
import util.ScreenInfo;

/**
 *
 * @author zkieda
 */
public class FadeOut implements State{
    private State next;

    public static final byte INDEPENDANT_STATE = 0;
    //states within states
    public static final byte INTERNAL_STATE = 1;
    private byte type;
    private char r, g, b;
    //fades out to an rgb color
    public FadeOut(int millis, State next, byte r, byte g, byte b, byte type){
        this.next = next;
        this.r = (char)(r&0xFF);
        this.g = (char)(g&0xFF);
        this.b = (char)(b&0xFF);
        this.mil = millis;
        this.type = type;
        t = System.currentTimeMillis();
    }
    final int mil;
    public void setNext(State state){
        this.next = state;
    }
    @Override
    public State nextState() {
//        byte[] b = new byte[3];
//        rr.nextBytes(b);
//        return new FadeOut(mil, null, b[0], b[1],b[2]);
        return next;
    }
    long time = System.currentTimeMillis();
    @Override public boolean isFinished() {
//        return i >= 255;
//        return false;
        return end;
    }
    private double i = 0;
    private boolean end = false;
    private long t;
    @Override public void update() {
        if(type == INTERNAL_STATE){
            i += (util.Time.get_dt()*255)/(double)mil;
            if(i>255){end = true; return;}
            awt_setColor(new Color(r,g,b,(int)i));
        } else if(type == INDEPENDANT_STATE){
            if(System.currentTimeMillis() - t > mil) {end = true; return;}
            awt_setColor(new Color(r,g,b,
                    (int)(127*(1d - Math.pow(255, -(util.Time.get_dt()/(double)(mil-System.currentTimeMillis() + t))))))
//                    (int)((util.Time.get_dt()*255*3.5)/(double)mil))
                    );
            /**
             * this calculation for alpha might seem strange at first, but it 
             * makes sense.
             * 
             * We want to go, completely, to the new color by fading. 
             * Because this is in a completely new routine, when we paint atop
             * of the bufferedimage it stacks. 
             * 
             * We investigate how a pixel is modified. For an arbitrary SOURCE 
             * color component c (that is from our original pixel), 
             * alpha component a from the new pixel, and color component of the
             * new pixel in, (all bounded [0,1]), we know that the blending to 
             * the new color component, c_out is
             * 
             * c_out = a*in + c*(1-a)
             * 
             * We see the recurrence formed from multiple iterations. (all with 
             * same alpha component and input color component)
             * 
             *      c_{n} = a*in + c_{n-1} * (1-a)
             * 
             * By induction, we see that this recurrence's closed form, for 
             * n iterations, is
             * 
             * c_{n} = a*in*(sum from (i \in [n-1]) of (1-a)^i)  + c*(1-a)^n
             * 
             * We approximately know the number of iterations, n, or the 
             * total time we are supposed to take divided by dt.
             * 
             * We want to solve for the alpha component used. We note that,
             * as the final value of the color reaches the new color, the 
             * original color goes to zero (has no influence) and the new color
             * goes to one (has complete influence). 
             * 
             * So, 
             *      a*in*(sum from (i \in [n-1]) of (1-a)^i) = 1
             * 
             * and 
             *      c*(1-a)^n = 0
             * However, the bounding condition implies that it would take an 
             * infinite number of steps to reach this (when alpha is not equal 
             * to 1). Because we are CS, we know that we just have to stop at 
             *      1/255
             * which is the precision of an 8 bit component. So, we solve for 
             * the alpha component.
             *      c*(1-a)^n = 1/255
             * 
             *      a = 1 - pow(255, -1/n)
             * 
             * Because we are using bytes and not floats, we multiply the float
             * by 255
             *      a = 255*(1 - pow(255, -1/n))
             * 
             * We do not know exactly how many steps we will take to get to 
             * zero, but we may approximate the number of steps at each interval
             * 
             * n = (time_for_completion - time_passed)/dt;
             * 
             * And we get an equation that works!
             */
        }
        awt_fillRect(0, 0, util.ScreenInfo.SCREEN_SIZE.width, util.ScreenInfo.SCREEN_SIZE.height);
    }
    @Override public MouseListener getMouseAdapter() { return null;}
    @Override public KeyListener getKeyAdapter() { return null;}
    @Override public WindowListener getWindowAdapter() { return null;}
}
