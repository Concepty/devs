package org.devs;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        SocketFactory customSocketFactory = new CustomSocketFactory();
        Socket customSocket = null;
        try {
            customSocket = customSocketFactory.createSocket();
            customSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}