package org.devs;

import java.net.SocketImpl;
import java.net.SocketImplFactory;

public class CustomSocketImplFactory implements SocketImplFactory {

    @Override
    public SocketImpl createSocketImpl() {
        return new CustomSocketImpl();
    }
}
