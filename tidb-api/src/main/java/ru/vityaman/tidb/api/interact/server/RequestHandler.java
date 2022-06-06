package ru.vityaman.tidb.api.interact.server;

public interface RequestHandler {
    void handleRequest(Request request, ResponseSender sender);
}
