package ru.vityaman.tidb.api.interact.net;

import java.io.Closeable;
import java.io.IOException;

public interface InetConnection extends Closeable {
    RecievedMessage recieve() throws IOException;
    InetConnection send(MessageToSend message) throws IOException;
}
