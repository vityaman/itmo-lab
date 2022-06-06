package ru.vityaman.tidb.api.interact.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayDeque;
import java.util.Queue;

import ru.vityaman.tidb.api.interact.MethodCall;
import ru.vityaman.tidb.api.serialization.SerializationStream;

public final class UDPServer implements Server {
    private boolean running;
    private final InetSocketAddress address;
    private final RequestHandler requestHandler;
    private final Queue<Response> responsesToSend;
    
    public UDPServer(
        InetSocketAddress address, RequestHandler requestHandler
    ) throws SocketException {
        this.address = address;
        this.running = false;
        this.requestHandler = requestHandler;
        responsesToSend = new ArrayDeque<>();
    }

    @Override
    public void run() {
        running = true;
        ByteBuffer buffer = ByteBuffer.allocate(1 << 16);
        try (DatagramChannel channel = DatagramChannel.open().bind(address)) {
            channel.configureBlocking(false);
            while (running) {
                buffer.clear();
                SocketAddress sender = channel.receive(buffer);
                buffer.flip();
                if (buffer.limit() != 0)
                    handleRequest(sender, buffer);
                Response response = responsesToSend.poll();
                if (response != null) send(channel, buffer, response);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    private void handleRequest(SocketAddress sender, ByteBuffer buffer) 
    throws IOException {
        ObjectInputStream input = new ObjectInputStream(
            new ByteArrayInputStream(buffer.array())
        );
        try {
            MethodCall call = (MethodCall) input.readObject();
            Request request = new Request(sender, call);
            requestHandler.handleRequest(request, this);
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void stop() {
        running = false;
    }

    private void send(
        DatagramChannel channel, ByteBuffer buffer, Response response
    ) throws IOException {
        buffer.clear();
        SerializationStream.empty()
                .writeSerializable(response.result())
                .writeTo(buffer);
        buffer.flip();
        channel.send(buffer.slice(), response.destination());
    }

    @Override
    public void sendResponse(Response response) {
        responsesToSend.add(response);
    }    
}
