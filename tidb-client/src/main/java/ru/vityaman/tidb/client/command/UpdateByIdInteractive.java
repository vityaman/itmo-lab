package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.api.exception.NoSuchEntryException;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.ui.input.Input;
import ru.vityaman.tidb.client.ui.input.RequestObject;
import ru.vityaman.tidb.client.ui.out.Out;

import java.io.IOException;
import java.util.List;


/**
 * Represents 'update' command.
 */
public final class UpdateByIdInteractive implements Executable {
    private final RequestObject request;
    private final RemoteTicketCollection tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets tickets to edit
     */
    public UpdateByIdInteractive(
            Input in, Out out, RemoteTicketCollection tickets
    ) {
        this.request = new RequestObject(in, out);
        this.tickets = tickets;
    }

    private void execute(int id) throws NoSuchEntryException, IOException {
        tickets.updateById(id, request.ticket());
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        try {
            execute((Integer) args.get(0));
        } catch (NoSuchEntryException | IOException e) {
            throw new ExecutionException(e);
        }
    }
}
