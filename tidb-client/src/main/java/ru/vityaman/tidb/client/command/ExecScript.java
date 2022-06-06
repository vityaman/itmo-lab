package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.api.interact.client.Client;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.serialization.VionTicketSerializer;
import ru.vityaman.tidb.client.ui.TidbConsole;
import ru.vityaman.tidb.client.ui.input.StreamInput;
import ru.vityaman.tidb.client.ui.out.Out;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public final class ExecScript implements Executable {
    private final Out out;
    private final Client client;
    private final VionTicketSerializer ticketSerializer;
    private final Set<Path> trace;

    public ExecScript(
        Out out, 
        Client client,
        VionTicketSerializer ticketSerializer,
        Set<Path> trace
    ) {
        this.out = out;
        this.client = client;
        this.ticketSerializer = ticketSerializer;
        this.trace = trace;
    }

    public void execute(String filepath) 
    throws FileNotFoundException, ExecutionException {
        Path path = Paths.get(filepath);
        if (trace.contains(path)) {
            throw new ExecutionException(String.format(
                "Recursion call of %s, trace: %s",
                path, trace.toString()
            ));
        }
        trace.add(path);
        TidbConsole console =
            new TidbConsole(
                new StreamInput(
                    new FileInputStream(
                        path.toFile()
                    )
                ), 
                out, 
                client,
                ticketSerializer,
                trace
            );  
        console.run();
        trace.remove(path);
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        try {
            execute((String) args.get(0));
        } catch (FileNotFoundException e) {
            throw new ExecutionException(e);
        }
    }
}
