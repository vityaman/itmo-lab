package ru.vityaman.tidb.api.interact.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;

import ru.vityaman.tidb.api.serialization.SerializationStream;

public interface EstablishedConnection extends Closeable {
    InetAddress partner();
    ObjectInputStream receive() throws IOException;
    EstablishedConnection send(SerializationStream data) throws IOException;
}
