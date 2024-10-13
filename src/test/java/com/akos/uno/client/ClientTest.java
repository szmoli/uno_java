package com.akos.uno.client;

import com.akos.uno.server.Server;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    @Test
    public void testStartConnection() {
        Server server = new Server();
        server.startServer(1326);

        ClientController cc = new ClientController(new Client());
        cc.startConnection("127.0.0.1", 1326);


    }
}
