package core;

import gui.MainFrame;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowListener;
import state.State;
import util.Time;

/**
 * @author zkieda
 */
public final class Core {
    private Core(){}
    private static State head;
    private static State current;
    protected static int clock_wait_time = 15;
    private static final Runnable core_thread = new Runnable() {
        @Override public void run() {
            while(true){
                Time.calculate_dt();
                current.update();
                MainFrame.repaint();
                if(current.isFinished()){
                    if((current = current.nextState()) == null) break;
                        //we have reached the last state,
                    setAdapters();
                }
                Time.sleep(clock_wait_time);
            }
            
            System.exit(0);
        }
    };
    private static void setAdapters(){
        assert current != null;
        //set the new adapters
        {KeyListener kl = current.getKeyAdapter();
        if(kl != State.NO_KEY_CHANGE) MainFrame.setKeyListener(kl);
        }{MouseListener ml = current.getMouseAdapter();
        
        if(ml != State.NO_MOUSE_CHANGE){
            if(ml instanceof MouseMotionListener) MainFrame.setMouseMotionListener((MouseMotionListener)ml);
            if(ml instanceof MouseWheelListener) MainFrame.setMouseWheelListener((MouseWheelListener)ml);
            MainFrame.setMouseListener(ml);
        }
        }{WindowListener wl = current.getWindowAdapter();
        if(wl != State.NO_WINDOW_CHANGE) MainFrame.setWindowListener(wl);
        }
    }
    public static void init(final State head){
        assert head != null;
        Core.head = head;
        current = Core.head;
        setAdapters();
        Thread t = new Thread(core_thread);
        t.start();
    }
    public static int getWaitTime(){
        return clock_wait_time;
    }
    public static void setWaitTime(int clock_wait_time){
        assert clock_wait_time > 0;
        Core.clock_wait_time = clock_wait_time;
    }
}
