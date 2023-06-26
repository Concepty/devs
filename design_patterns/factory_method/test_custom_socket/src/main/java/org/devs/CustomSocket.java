package org.devs;

import java.io.IOException;
import java.net.Socket;

public class CustomSocket extends Socket {
    public synchronized void close() throws IOException {
        System.out.println("called: custom socket close");
        super.close();
    }
}
