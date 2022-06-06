package ru.vityaman.tidb.api.interact.net;

import java.net.InetAddress;

public final class RecievedMessage extends Message {
    private InetAddress sender;

    public RecievedMessage(InetAddress sender, byte[] payload) {
        super(payload);
        this.sender = sender;
    }

    public InetAddress sender() {
        return sender;
    }
}
