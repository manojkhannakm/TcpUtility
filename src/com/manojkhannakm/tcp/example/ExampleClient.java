package com.manojkhannakm.tcp.example;

import com.manojkhannakm.tcp.client.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @author Manoj Khanna
 */

public class ExampleClient {

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connect(InetAddress.getLocalHost(), 1999);

        new Thread(() -> {
            while (true) {
                try {
                    System.out.println(client.receive());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner in = new Scanner(System.in);
        while (true) {
            client.send(in.nextLine());
        }
    }

}
