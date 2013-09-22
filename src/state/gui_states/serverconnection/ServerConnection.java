package state.gui_states.serverconnection;

import state.State;

/**
 * @author zkieda
 */
public interface ServerConnection extends State{
    public String serverName();
    
    //return null if this player does not want to participate. 
    public String playerName();
}
