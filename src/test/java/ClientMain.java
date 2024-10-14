import com.akos.uno.client.Client;
import com.akos.uno.client.ClientController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientMain {
    public static void main(String[] args) {
        String name = args[0];
        String address = args[1];
        int port = Integer.parseInt(args[2]);

        ClientController cc = new ClientController(name);
        cc.startConnection(address, port);
    }
}
