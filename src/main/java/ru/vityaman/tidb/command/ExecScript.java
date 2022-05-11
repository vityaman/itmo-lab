package ru.vityaman.tidb.command;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import ru.vityaman.tidb.collection.TicketsFromFile;
import ru.vityaman.tidb.collection.json.serialize.TicketSerialization;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.ui.TidbConsole;
import ru.vityaman.tidb.ui.input.StreamInput;
import ru.vityaman.tidb.ui.out.Out;

public final class ExecScript implements Executable {
    private final Out out;
    private final TicketsFromFile tickets;
    private final TicketSerialization ticketSerialization;
    private final Set<Path> trace;

    public ExecScript(
        Out out, 
        TicketsFromFile tickets, 
        TicketSerialization ticketSerialization,
        Set<Path> trace
    ) {
        this.out = out;
        this.tickets = tickets;
        this.ticketSerialization = ticketSerialization;
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
                tickets, 
                ticketSerialization,
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
