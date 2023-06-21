package org.devs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        CustomSocket sock = null;
        try {
            sock = new CustomSocket("localhost", 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            sock.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}