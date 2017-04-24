package com.manojkhannakm.tcp.server;

import java.io.*;
import java.net.Socket;

/**
 * @author Manoj Khanna
 */

public class Client {

    private boolean connected = true;
    private Server server;
    private Socket socket;

    public Client(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void disconnect() throws IOException {
        if (!connected) {
            return;
        }

        connected = false;

        server.callback.clientDisconnected(this);

        socket.close();
    }

    public void send(String s) throws IOException {
        if (!connected) {
            return;
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write(s + System.lineSeparator());
        writer.flush();
    }

    public String receive() throws IOException {
        if (!connected) {
            return null;
        }

        return new BufferedReader(new InputStreamReader(socket.getInputStream()))
                .readLine();
    }

    public boolean isConnected() {
        return connected;
    }

    public String getName() {
        return socket.getInetAddress().getHostName();
    }

}
