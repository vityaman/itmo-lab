package ru.vityaman.tidb.api.interact.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public final class UDPConnection implements InetConnection {
    private final DatagramPacket packet;
    private final DatagramSocket socket;

    public UDPConnection(int bufferCapacity) throws SocketException {
        ensureValidBufferCapacity(bufferCapacity);
        byte[] bytes = new byte[bufferCapacity];
        packet = new DatagramPacket(bytes, bytes.length);
        this.socket = new DatagramSocket();
    }

    public UDPConnection() throws SocketException {
        this(100_000);
    }

    @Override
    public RecievedMessage recieve() throws IOException {
        socket.receive(packet);
        return new RecievedMessage(
            packet.getAddress(), 
            packet.getData()
        );
    }

    @Override
    public InetConnection send(MessageToSend message) throws IOException {
        message.payload().read(packet.getData());
        packet.setAddress(message.destination());
        socket.send(packet);
        return this;
    }

    @Override
    public void close() throws IOException {
        socket.disconnect();
        socket.close();
    }
    
    private void ensureValidBufferCapacity(int bufferCapacity) {
        if (bufferCapacity < 1) {
            throw new IllegalArgumentException(String.format(
                "bufferCapacity(%s) not in [1, INT_MAX]", bufferCapacity
            ));
        }
    }
}
