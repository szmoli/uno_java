import com.akos.uno.client.Client;
import com.akos.uno.client.ClientController;
import com.akos.uno.communication.action.DiscardCardAction;
import com.akos.uno.game.Card;
import com.akos.uno.game.CardColor;
import com.akos.uno.game.CardSymbol;
import com.akos.uno.game.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientMain {
    public static void main(String[] args) {
        String name = args[0];
        String address = args[1];
        int port = Integer.parseInt(args[2]);
        Player player = new Player(name);

        ClientController cc = new ClientController(player);
        cc.startConnection(address, port);
        cc.sendMessageToServer(new DiscardCardAction(player.getPlayerName(), new Card(CardColor.RED, CardSymbol.SEVEN)).getAsJson());
    }
}
