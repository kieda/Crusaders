package state.gui_states.serverconnection;

import state.State;

/**
 * @author zkieda
 */
public interface PlayerConnection extends State {
    //sent from the player to the server.
    public String gameName();
    public String playerName();
    
    //sent from the server to the player. Tells them whether or not their
    //choice was correct or incorrect.
    public void validGameName(boolean b);
    public void validPlayerName(boolean b);
}
