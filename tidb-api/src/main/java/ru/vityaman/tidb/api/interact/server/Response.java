package ru.vityaman.tidb.api.interact.server;

import java.net.SocketAddress;

import ru.vityaman.tidb.api.interact.Result;

public final class Response {
    private final SocketAddress destination;
    private final Result result;

    public Response(SocketAddress destination, Result result) {
        this.destination = destination;
        this.result = result;
    }

    public SocketAddress destination() {
        return destination;
    }

    public Result result() {
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "Response[to=%s data=%s]",
                destination, result
        );
    }
}
