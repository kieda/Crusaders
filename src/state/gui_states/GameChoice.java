package state.gui_states;

import state.State;

/**
 * @author zkieda
 */
public interface GameChoice extends State{
    //Prompt the user on whether he/she wants to be a server or a player. 
    //return true if server, false if player.
    //will be called after isFinished returns true.
    public boolean serverOrPlayer();
    
}
