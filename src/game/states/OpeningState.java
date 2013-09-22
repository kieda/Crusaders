package game.states;

import static graphics.AWT.awt_drawImage;
import static graphics.AWT.awt_fillRect;
import static graphics.AWT.awt_setColor;
import static graphics.PPP.process;
import static graphics.PPP.process2;
import graphics.anim.FadeOut;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import state.State;
import util.ScreenInfo;

/**
 * The opening state for the crusaders game...
 * @author zkieda
 */
public class OpeningState implements State{
        public boolean to_exit = false;
        public static final int logo_pos_y = (ScreenInfo.SCREEN_SIZE.height - graphics.sprites.logo.Logo3.LOGO.getHeight())>>1;
        public final static int logo_pos_x = (ScreenInfo.SCREEN_SIZE.width - graphics.sprites.logo.Logo3.LOGO.getWidth())>>1;
        public final static double OPENING_TIME = 750d;
        @Override
        public State nextState() {
            return new FadeOut(1000, null, (byte)Color.BLACK.getRed(), (byte)Color.BLACK.getGreen(), (byte)Color.BLACK.getBlue(), FadeOut.INDEPENDANT_STATE);
        }
        @Override public boolean isFinished() {
            return to_exit;
        }
        BufferedImage rand = new BufferedImage(ScreenInfo.SCREEN_SIZE.width, ScreenInfo.SCREEN_SIZE.height,  BufferedImage.TYPE_INT_ARGB);{
            Graphics2D g = rand.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, ScreenInfo.SCREEN_SIZE.width, ScreenInfo.SCREEN_SIZE.height);
        }
        private double i = 255;
        @Override public void update() {
            process(rand, StatePool.RANDOM_GAUSSIAN_SPECULATION);
            awt_drawImage(rand, 0,0, null);
            process2(StatePool.GAUSSIAN_BLUR);
            process(StatePool.CENTERED_GRADIENT);
            
            awt_drawImage(graphics.sprites.logo.Logo3.LOGO, logo_pos_x,  logo_pos_y, null);
            if(i>=0){
                awt_setColor(new Color(0,0,0,(int)i));
                awt_fillRect(0, 0, ScreenInfo.SCREEN_SIZE.width, ScreenInfo.SCREEN_SIZE.height);
                i -= (util.Time.get_dt()*255)/OPENING_TIME;
            }
        }
        @Override public MouseListener getMouseAdapter() {return new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mwe) {
                i += .01*mwe.getWheelRotation();
            }
        }; }
        @Override public KeyListener getKeyAdapter() { return new KeyAdapter() {
            @Override
                public void keyPressed(KeyEvent ke) {
                    if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) to_exit = true;
                }
        };}
        @Override public WindowListener getWindowAdapter() {return null;}
}
