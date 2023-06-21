package org.devs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;

public class CustomSocketImpl extends SocketImpl {
    @Override
    protected void create(boolean b) throws IOException {
        System.out.println("create");
    }

    @Override
    protected void connect(String s, int i) throws IOException {
        System.out.println("connect");
    }

    @Override
    protected void connect(InetAddress inetAddress, int i) throws IOException {
        System.out.println("connect");
    }

    @Override
    protected void connect(SocketAddress socketAddress, int i) throws IOException {
        System.out.println("connect");
    }

    @Override
    protected void bind(InetAddress inetAddress, int i) throws IOException {
        System.out.println("bind");
    }

    @Override
    protected void listen(int i) throws IOException {
        System.out.println("listen");
    }

    @Override
    protected void accept(SocketImpl socket) throws IOException {
        System.out.println("accept");
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        System.out.println("getInputStream");
        return null;
    }

    @Override
    protected OutputStream getOutputStream() throws IOException {
        System.out.println("getOutputStream");
        return null;
    }

    @Override
    protected int available() throws IOException {
        System.out.println("available");
        return 0;
    }

    @Override
    protected void close() throws IOException {
        System.out.println("close");
    }

    @Override
    protected void sendUrgentData(int i) throws IOException {
        System.out.println("sendUrgentData");
    }

    @Override
    public void setOption(int i, Object o) throws SocketException {
        System.out.println("setOption");
    }

    @Override
    public Object getOption(int i) throws SocketException {
        System.out.println("getOption");
        return null;
    }
}
