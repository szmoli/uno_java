import com.akos.uno.game.GameController;
import com.akos.uno.game.GameModel;
import com.akos.uno.game.GameRules;
import com.akos.uno.server.Server;

public class ServerMain {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        Server server = new Server();
        server.startServer(port);
    }
}
