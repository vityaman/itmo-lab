package ru.vityaman.tidb.command;

import java.io.File;
import java.util.List;

import ru.vityaman.tidb.data.resource.TicketsStorage;
import ru.vityaman.tidb.lang.interpreter.Executable;

public final class DumpArgument implements Executable {
    private final TicketsStorage storage;

    public DumpArgument(TicketsStorage storage) {
        this.storage = storage;
    }

    public void execute(String path) {
        storage.save(new File(path));
    }

    @Override
    public void execute(List<Object> args) {
        execute((String) args.get(0));
    }
}
