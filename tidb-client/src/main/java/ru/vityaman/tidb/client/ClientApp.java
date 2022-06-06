package ru.vityaman.tidb.client;

import java.io.IOException;
import java.nio.file.Paths;

import ru.vityaman.tidb.api.interact.client.Client;
import ru.vityaman.tidb.api.serialization.CoordinatesSerializer;
import ru.vityaman.tidb.api.serialization.EntrySetSerializer;
import ru.vityaman.tidb.api.serialization.LocationSerializer;
import ru.vityaman.tidb.api.serialization.PersonSerializer;
import ru.vityaman.tidb.api.serialization.TicketEntrySerializer;
import ru.vityaman.tidb.api.serialization.TicketSerializer;
import ru.vityaman.tidb.client.Configuration.ReadingException;
import ru.vityaman.tidb.client.ui.App;
import ru.vityaman.tidb.client.ui.input.StreamInput;
import ru.vityaman.tidb.client.ui.out.StreamOut;

public class ClientApp {
    public static void main(String[] args) 
    throws ReadingException {
        Configuration configuration = Configuration.load(
            Paths.get("res", "client-config.yml")
        );

        EntrySetSerializer entrySetSerializer = new EntrySetSerializer(
                new TicketEntrySerializer(
                        new TicketSerializer(
                                new CoordinatesSerializer(),
                                new PersonSerializer(
                                        new LocationSerializer()
                                )
                        )
                )
        );

        try (
            Client client = new Client(
                configuration.connection().server().address(),
                entrySetSerializer
            )
        ) {
            App app = new App(
                    new StreamOut(System.out),
                    new StreamInput(System.in),
                    client
            );
            app.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}