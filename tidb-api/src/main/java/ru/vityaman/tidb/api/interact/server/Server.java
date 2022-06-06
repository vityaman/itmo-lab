package ru.vityaman.tidb.api.interact.server;

public interface Server extends Runnable, ResponseSender {
    void stop();
}
