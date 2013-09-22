
package game.states;

import graphics.AWT;
import graphics.perpixel.CircleGradient;
import graphics.perpixel.SimpleGaussianBlur;
import graphics.perpixel.Speculate;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import state.State;
import util.ScreenInfo;

/**
 * Just a pool of States that are used for the game.
 * @author zkieda
 */
public class StatePool {
    
    public static final CircleGradient CENTERED_GRADIENT = new CircleGradient(ScreenInfo.SCREEN_SIZE.width>>1, ScreenInfo.SCREEN_SIZE.height>>1){{setRadius(ScreenInfo.smallest_d()>>1);}};
    
    public static final Speculate RANDOM_GAUSSIAN_SPECULATION = new Speculate(4);
    
    public static final SimpleGaussianBlur GAUSSIAN_BLUR = new SimpleGaussianBlur();
    
    public static final State OPENING_STATE = new OpeningState();
}
