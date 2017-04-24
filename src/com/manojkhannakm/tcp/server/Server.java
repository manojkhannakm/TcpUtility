package com.manojkhannakm.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * @author Manoj Khanna
 */

public class Server {

    Callback callback;

    private boolean connected;
    private ServerSocket serverSocket;
    private ArrayList<Client> clientList;
    private Thread thread;

    public void connect(int port) throws IOException {
        if (connected) {
            return;
        }

        connected = true;
        serverSocket = new ServerSocket(port);
        clientList = new ArrayList<>();

        thread = new Thread(() -> {
            while (connected) {
                try {
                    Client client = new Client(this, serverSocket.accept());
                    clientList.add(client);

                    if (callback != null) {
                        callback.clientConnected(client);
                    }
                } catch (IOException ignored) {
                }
            }
        });
        thread.start();
    }

    public void disconnect() throws IOException {
        if (!connected) {
            return;
        }

        connected = false;

        try {
            thread.join();
        } catch (InterruptedException ignored) {
        }

        for (Client client : clientList) {
            client.disconnect();
        }

        serverSocket.close();
    }

    public boolean isConnected() {
        return connected;
    }

    public ArrayList<Client> getClientList() {
        ArrayList<Client> clientList = new ArrayList<>();
        for (Client client : this.clientList) {
            if (client.isConnected()) {
                clientList.add(client);
            }
        }

        return clientList;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {

        void clientConnected(Client client);

        void clientDisconnected(Client client);

    }

}
