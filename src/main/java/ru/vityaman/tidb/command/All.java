package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.collection.CollectionFromDataset;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents a 'all' command, that prints
 * all tickets from collection.
 */
public final class All implements Executable {
    private final Out out;
    private final CollectionFromDataset tickets;

    /**
     * @param out where to print.
     * @param tickets source of collection.
     */
    public All(Out out, CollectionFromDataset tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    private void execute() {
        tickets.all().stream()
        .forEach((e) -> 
            out.println(
                e.key() + ": " + e.ticket().toString()
            )
        );
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
