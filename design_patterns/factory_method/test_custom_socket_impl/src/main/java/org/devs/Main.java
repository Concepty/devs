package org.devs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        testChangeSocketImplFactory();
    }

    public static void testCustomSocket() {
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

    public static void testChangeSocketImplFactory() {
        Socket originalSock = null;
        Socket customSock = null;
        try {
            originalSock = new Socket();
            Socket.setSocketImplFactory(new CustomSocketImplFactory());
            customSock = new Socket("127.0.0.1", 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            customSock.close();
            System.out.println("customSock closed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            originalSock.close();
            System.out.println("is originalSock changed?");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
