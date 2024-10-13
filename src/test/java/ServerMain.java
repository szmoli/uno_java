import com.akos.uno.game.GameModel;
import com.akos.uno.game.GameRules;
import com.akos.uno.server.Server;

public class ServerMain {
    public static void main(String[] args) {
        GameRules rules = new GameRules(false, false, false, false, 7, 2, 10);
        GameModel game = new GameModel(rules);

        int port = Integer.parseInt(args[0]);

        Server server = new Server(game);
        server.startServer(port);
    }
}
