package ru.vityaman.tidb.server;

import java.io.IOException;
import java.net.SocketException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import ru.vityaman.tidb.api.collection.Entry;
import ru.vityaman.tidb.api.interact.Result;
import ru.vityaman.tidb.api.interact.server.Server;
import ru.vityaman.tidb.api.interact.server.UDPServer;
import ru.vityaman.tidb.api.serialization.*;
import ru.vityaman.tidb.server.Configuration.ReadingException;

public class ServerApp {
    public static void main(String[] args) 
    throws SocketException, ReadingException 
    {
        Configuration configuration = Configuration.load(
            Paths.get("res", "server-config.yml")
        );

        EntrySetSerializer entrySetSerializator = new EntrySetSerializer(
            new TicketEntrySerializer(
                new TicketSerializer(
                    new CoordinatesSerializer(),
                    new PersonSerializer(
                        new LocationSerializer()
                    )
                )
            )
        );

        Set<Entry> entries = new HashSet<>();

        Server server = new UDPServer(
            configuration.connection().address(),
            (request, sender) -> {
                try {
                    System.out.println(request);
                    sender.sendResponse(
                        request.answer(new Result(
                            SerializationStream.empty()
                                .writeUsingSerializer(
                                        entries,
                                        entrySetSerializator
                                )
                                .bytes()
                        ))
                    );
                } catch (IOException ignored) {}
            }
        );
        server.run();
    }
}
