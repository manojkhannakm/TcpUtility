package com.manojkhannakm.tcp.example;

import com.manojkhannakm.tcp.server.Client;
import com.manojkhannakm.tcp.server.Server;

import java.io.IOException;

/**
 * @author Manoj Khanna
 */

public class ExampleServer {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.setCallback(new Server.Callback() {

            @Override
            public void clientConnected(Client client) {
                new Thread(() -> {
                    while (true) {
                        try {
                            String s = client.getName() + ": " + client.receive();
                            System.out.println(s);

                            for (Client c : server.getClientList()) {
                                c.send(s);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void clientDisconnected(Client client) {
            }

        });
        server.connect(1999);
    }

}
