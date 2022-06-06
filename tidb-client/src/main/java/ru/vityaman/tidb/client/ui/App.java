package ru.vityaman.tidb.client.ui;

import ru.vityaman.tidb.api.interact.client.Client;
import ru.vityaman.tidb.api.serialization.*;
import ru.vityaman.tidb.client.serialization.VionCoordinatesSerializer;
import ru.vityaman.tidb.client.serialization.VionLocationSerializer;
import ru.vityaman.tidb.client.serialization.VionPersonSerializer;
import ru.vityaman.tidb.client.serialization.VionTicketSerializer;
import ru.vityaman.tidb.client.ui.input.Input;
import ru.vityaman.tidb.client.ui.out.Out;

import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashSet;

/**
 * Ticket DB App.
 */
public final class App implements Runnable {
    private final Out out;
    private final TidbConsole interpreter;

    /**
     * @param out where to print
     * @param in where to input
     */
    public App(Out out, Input in, Client client)
            throws SocketException {
        this.out = out;

        VionTicketSerializer ticketSerialization =
            new VionTicketSerializer(
                new VionCoordinatesSerializer(),
                new VionPersonSerializer(
                    new VionLocationSerializer()
                )
            );

        interpreter = new TidbConsole(in, out,
            client,
            ticketSerialization,
            new HashSet<>()
        );
    }

    @Override
    public void run() {
        out.println("Welcome! Print 'help' to list all command!");
        interpreter.run();
    }
}
