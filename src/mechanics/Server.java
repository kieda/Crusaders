package mechanics;

import java.util.HashMap;
import mechanics.objects.Player;

/**
 * @author zkieda
 */
public class Server {
    private static int num_players = 0;
    
    private static final class ClientPlayerPair{
        private ClientPlayerPair(Client c, Player p){
            assert c != null && p != null;
            assert c.hashCode() == p.hashCode();
            this.c = c; this.p = p;
        }
        
        final Client c; final Player p;
    }
    private static final HashMap<Integer, ClientPlayerPair> PLAYERS = new HashMap<Integer, ClientPlayerPair>();
    
}
