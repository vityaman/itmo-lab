package ru.vityaman.tidb.command;

import java.io.File;
import java.util.List;

import ru.vityaman.tidb.data.resource.TicketsStorage;
import ru.vityaman.tidb.lang.interpreter.Executable;

public final class Open implements Executable {
    public final TicketsStorage storage;

    public Open(TicketsStorage storage) {
        this.storage = storage;
    }

    public void execute(String filepath) {
        storage.open(new File(filepath));
    }

    @Override
    public void execute(List<Object> args) {
        execute((String) args.get(0));
    }
}
