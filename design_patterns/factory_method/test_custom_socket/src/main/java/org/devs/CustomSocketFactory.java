package org.devs;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class CustomSocketFactory extends SocketFactory {

    @Override
    public Socket createSocket() throws IOException {
        return new CustomSocket();
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
        return new CustomSocket();
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
        return new CustomSocket();
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return new CustomSocket();
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
        return new CustomSocket();
    }
}
