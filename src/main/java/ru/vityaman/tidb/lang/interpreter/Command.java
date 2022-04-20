package ru.vityaman.tidb.lang.interpreter;

/**
 * Represents interpreter command.
 */
public final class Command {
    private final Signature signature;
    private final Executable executable;

    /**
     * @param signature signature of the command.
     * @param executable what to execute when command called.
     */
    public Command(Signature signature,
                   Executable executable) {
        this.signature = signature;
        this.executable = executable;
    }

    /**
     * @return signature.
     */
    public Signature signature() {
        return signature;
    }

    /**
     * @return attached executable.
     */
    public Executable executable() {
        return executable;
    }

    public static Command of(Executable executable,
                             String name,
                             Class<?>... types) {
        return new Command(new Signature(name, types), executable);
    }
}
