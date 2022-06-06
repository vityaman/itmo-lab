package ru.vityaman.tidb.api.interact.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import ru.vityaman.tidb.api.serialization.SerializationStream;

public final class EstablishedUDPConnection implements EstablishedConnection {
    private static final int DEFAULT_BUFFER_CAPACITY = 100_000;

    private final DatagramPacket packet;
    private final DatagramSocket socket;

    public EstablishedUDPConnection(
        SocketAddress partner, int bufferCapacity
    ) throws SocketException {
        this.socket = new DatagramSocket();
        socket.connect(partner);
        byte[] bytes = new byte[bufferCapacity];
        packet = new DatagramPacket(bytes, bytes.length);
    }

    public EstablishedUDPConnection(SocketAddress partner) throws SocketException {
        this(partner, DEFAULT_BUFFER_CAPACITY);
    }

    @Override
    public InetAddress partner() {
        return socket.getInetAddress();
    }

    @Override
    public ObjectInputStream receive() throws IOException {
        socket.receive(packet);
        return new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
    }

    @Override
    public EstablishedConnection send(SerializationStream data) throws IOException {
        packet.setLength(data.bytes().length);
        data.writeTo(packet.getData());
        socket.send(packet);
        return this;
    }

    @Override
    public void close() {
        socket.disconnect();
        socket.close();
    }
}
