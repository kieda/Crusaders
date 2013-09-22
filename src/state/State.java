package state;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;

public interface State {
    public static final KeyListener NO_KEY_CHANGE = new KeyAdapter() {};
    public static final MouseListener NO_MOUSE_CHANGE = new MouseAdapter() {};
    public static final WindowListener NO_WINDOW_CHANGE = new WindowAdapter() {};
    /**
     * returns the next state in the sequence. 
     * 
     * Should be called only after isFinished returns true.
     * 
     * update() may also call graphics updates. This is done through the 
     * singleton graphics.AWT
     */
    State nextState();
    //if the state is finished, we return true to indicate we will move to the 
    //next state.
    boolean isFinished();
    //updates this state. Done through graphics.AWT
    void update();
    
    //the current mouse, key, and WindowAdapter for this State.
    //removed once this State is finished.
    MouseListener getMouseAdapter();
    KeyListener getKeyAdapter();
    WindowListener getWindowAdapter();
}
