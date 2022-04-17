package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.file.JsonFile;
import ru.vityaman.tidb.data.json.JsonTicketsStorage;
import ru.vityaman.tidb.data.resource.exception.SaveInabilityException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.printer.Printer;
import ru.vityaman.tidb.ui.request.RequestPrimitive;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a 'save' command.
 */
public final class Save implements Executable {
    private final Input in;
    private final JsonTicketsStorage storage;
    private final Printer out;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param storage storage to save
     */
    public Save(Input in, Printer out, JsonTicketsStorage storage) {
        this.in = in;
        this.out = out;
        this.storage = storage;
    }

    @Override
    public void execute(List<Object> args) {
        try {
            storage.save();
        } catch (SaveInabilityException e) {
            new RequestPrimitive<>("Save to: ",
                    (string) -> {
                        new JsonFile(string).write(storage
                                .collection()
                                .json());
                        return null;
                    },
                    new HashSet<Class<? extends Exception>>() {{
                            add(IOException.class);
            }}).from(in, out);
        }
    }
}
