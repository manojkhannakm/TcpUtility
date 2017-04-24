package com.manojkhannakm.tcp.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Manoj Khanna
 */

public class Client {

    private boolean connected;
    private Socket socket;

    public void connect(InetAddress inetAddress, int port) throws IOException {
        if (connected) {
            return;
        }

        connected = true;
        socket = new Socket(inetAddress, port);
    }

    public void disconnect() throws IOException {
        if (!connected) {
            return;
        }

        connected = false;
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

}
