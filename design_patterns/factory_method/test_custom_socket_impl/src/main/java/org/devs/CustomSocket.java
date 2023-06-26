package org.devs;

import java.io.IOException;
import java.net.Socket;

public class CustomSocket extends Socket {
    static {
        try{
            setSocketImplFactory(new CustomSocketImplFactory());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public CustomSocket(String host, int port) throws IOException {
        super(host, port);
    }
}