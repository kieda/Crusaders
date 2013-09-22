package gui;

import graphics.AWT;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import util.ScreenInfo;

/**
 * @author zkieda
 */
public class MainFrame {
    public static void repaint(){
        main_frame.repaint();
    }
    private static final JFrame main_frame = new JFrame(){
        @Override public void paint(Graphics g){
            java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
            Point top_left = ScreenInfo.paint_pos();
            Dimension image_size = ScreenInfo.paint_d();
            g2.drawImage(AWT.awt_getImage(), top_left.x, top_left.y, image_size.width, image_size.height, this);
        }
    };
    private static MouseListener current_ml;
    private static MouseMotionListener current_mml;
    private static MouseWheelListener current_mwl;
    private static WindowListener current_wl;
    private static KeyListener current_kl;
    private static boolean ml_isnt_null = false;//quicker than computing current_ml != null consistantly
    private static boolean mml_isnt_null = false;
    private static boolean mwl_isnt_null = false;
    private static boolean kl_isnt_null = false;
    private static boolean wl_isnt_null = false;
    
    /**
     * these three listeners are intended to marginally increase the speed
     * at which we can swap key listeners and mouse listeners. Before, using the
     * JFrame add*Listener and remove*Listener could take longer, as the JFrame
     * allows for multiple KeyListeners, and updates each one in sequence. 
     * 
     * However, finding and removing each one constantly with different states
     * is slower relative to just changing a boolean value / the behavior of a 
     * single listener.
     */
    private static final MouseListener central_ml = new MouseListener() {
        @Override public void mouseClicked(MouseEvent me) {
            if(ml_isnt_null) current_ml.mouseClicked(me);
        } @Override public void mousePressed(MouseEvent me) {
            if(ml_isnt_null) current_ml.mousePressed(me);
        } @Override public void mouseReleased(MouseEvent me) {
            if(ml_isnt_null) current_ml.mouseReleased(me);
        } @Override public void mouseEntered(MouseEvent me) {
            if(ml_isnt_null) current_ml.mouseEntered(me);
        } @Override public void mouseExited(MouseEvent me) {
            if(ml_isnt_null) current_ml.mouseExited(me);
        }
    };
    private static final KeyListener central_kl = new KeyListener() {
        @Override public void keyTyped(KeyEvent ke) {
            if(kl_isnt_null) current_kl.keyTyped(ke);
        } @Override public void keyPressed(KeyEvent ke) {
            if(kl_isnt_null) current_kl.keyPressed(ke);
        } @Override public void keyReleased(KeyEvent ke) {
            if(kl_isnt_null) current_kl.keyReleased(ke);
        }
    };
    private static final WindowListener central_wl = new WindowListener() {
          @Override public void windowOpened(WindowEvent we) {
              if(wl_isnt_null) current_wl.windowOpened(we);
        } @Override public void windowClosing(WindowEvent we) {
            if(wl_isnt_null) current_wl.windowClosing(we);
        } @Override public void windowClosed(WindowEvent we) {
            if(wl_isnt_null) current_wl.windowClosed(we);
        } @Override public void windowIconified(WindowEvent we) {
            if(wl_isnt_null) current_wl.windowIconified(we);
        } @Override public void windowDeiconified(WindowEvent we) {
            if(wl_isnt_null) current_wl.windowDeiconified(we);
        } @Override public void windowActivated(WindowEvent we) {
            if(wl_isnt_null) current_wl.windowActivated(we);
        } @Override public void windowDeactivated(WindowEvent we) {
            if(wl_isnt_null) current_wl.windowDeactivated(we);
        }
    };
    private static final MouseWheelListener central_mwl = new MouseWheelListener() {
        @Override public void mouseWheelMoved(MouseWheelEvent mwe) {
            if(mwl_isnt_null) current_mwl.mouseWheelMoved(mwe);
        }
    };
    public static int translate_x(int screen_x){
        return (screen_x-ScreenInfo.paint_pos().x)*ScreenInfo.SCREEN_SIZE.width/ScreenInfo.paint_d().width;
    }
    public static int translate_y(int screen_y){
        return (screen_y-ScreenInfo.paint_pos().x)*ScreenInfo.SCREEN_SIZE.width/ScreenInfo.paint_d().width;
    }
    private static final MouseMotionListener central_mml = new MouseMotionListener() {
          @Override public void mouseDragged(MouseEvent me) {
            if(mml_isnt_null) current_mml.mouseDragged(me);
        } @Override public void mouseMoved(MouseEvent me) {
            if(mml_isnt_null) current_mml.mouseMoved(me);
        }
    };
    public static void setMouseListener(MouseListener ml){
        ml_isnt_null = (current_ml = ml) != null;
    }
    public static void setMouseWheelListener(MouseWheelListener mwl){
        mwl_isnt_null = (current_mwl = mwl) != null;
    }
    public static void setMouseMotionListener(MouseMotionListener mml){
        mml_isnt_null = (current_mml = mml) != null;
    }
    public static void setKeyListener(KeyListener kl){
        kl_isnt_null = (current_kl = kl) != null;
    }
    public static void setWindowListener(WindowListener wl){
        wl_isnt_null = (current_wl = wl) != null;
    }
    static {
        main_frame.addKeyListener(central_kl);
        main_frame.addMouseListener(central_ml);
        main_frame.addMouseMotionListener(central_mml);
        main_frame.addMouseWheelListener(central_mwl);
        main_frame.addWindowListener(central_wl);
    }
    //repaint the background
    public static void recalc_frame(){
        Dimension screen_d = ScreenInfo.sc_d();
        main_frame.setBackground(Color.DARK_GRAY);
        main_frame.setBounds(0, 0, screen_d.width, screen_d.height);
    }
    public static void init(){
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.setUndecorated(true);
        Dimension screen_d = ScreenInfo.sc_d();
        main_frame.setBackground(Color.BLACK);
        main_frame.setBounds(0, 0, screen_d.width, screen_d.height);
        main_frame.setVisible(true);
    }
}
