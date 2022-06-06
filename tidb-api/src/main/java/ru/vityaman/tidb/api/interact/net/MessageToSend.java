package ru.vityaman.tidb.api.interact.net;

import java.net.InetAddress;

public final class MessageToSend extends Message {
    private final InetAddress destination;

    MessageToSend(InetAddress destination, byte[] payload) {
        super(payload);
        this.destination = destination;
    }

    public final InetAddress destination() {
        return destination;
    }
}
