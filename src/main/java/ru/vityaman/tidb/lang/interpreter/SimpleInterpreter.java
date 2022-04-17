package ru.vityaman.tidb.lang.interpreter;

import ru.vityaman.tidb.command.Command;
import ru.vityaman.tidb.command.Executable;
import ru.vityaman.tidb.command.exception.NoSuchCommandException;

import java.util.HashMap;
import java.util.Map;

public class SimpleInterpreter implements Interpreter {
    private final Map<String, Map<Signature, Executable>> commandBySignatureByKeyword;

    public SimpleInterpreter() {
        this.commandBySignatureByKeyword = new HashMap<>();
    }

    @Override
    public void execute(Instruction instruction) {
        commandBySignature(instruction.signature())
                .execute(instruction.arguments());
    }

    private Executable commandBySignature(Signature signature) {
        Map<Signature, Executable> commands
                = commandBySignatureByKeyword.get(signature.name());
        if (commands == null) {
            throw new NoSuchCommandException(signature.name());
        }
        for (Signature original : commands.keySet()) {
            if (signature.fitsTo(original)) {
                return commands.get(original);
            }
        }
        throw new NoSuchCommandException(signature.name());
    }

    public void load(Command command) {
        commandBySignatureByKeyword.computeIfAbsent(
                command.signature().name(),
                (k) -> new HashMap<>()
        ).put(command.signature(), command.executable());
    }
}
