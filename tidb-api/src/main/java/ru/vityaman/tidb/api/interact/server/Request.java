package ru.vityaman.tidb.api.interact.server;

import java.net.SocketAddress;

import ru.vityaman.tidb.api.interact.MethodCall;
import ru.vityaman.tidb.api.interact.Result;

public final class Request {
    private final SocketAddress sender;
    private final MethodCall methodCall;

    public Request(SocketAddress sender, MethodCall call) {
        this.sender = sender;
        this.methodCall = call;
    }

    public SocketAddress sender() {
        return sender;
    }    

    public MethodCall methodCall() {
        return methodCall;
    }

    public Response answer(Result result) {
        return new Response(sender, result);
    }

    @Override
    public String toString() {
        return String.format(
            "User with adress %s want to call %s",
            sender, methodCall
        );
    }
}
