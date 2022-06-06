package ru.vityaman.tidb.api.interact.client;

import java.net.SocketAddress;

public interface ClientConfiguration {
    SocketAddress serverAddress();
    int maxBufferSizeInBytes();
}
