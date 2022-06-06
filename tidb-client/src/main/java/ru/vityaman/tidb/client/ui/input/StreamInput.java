package ru.vityaman.tidb.client.ui.input;

import java.io.InputStream;
import java.util.Scanner;

public final class StreamInput implements Input {
    private final Scanner in;

    public StreamInput(InputStream in) {
        this.in = new Scanner(in);
    }

    @Override
    public String readLine() {
        if (in.hasNextLine()) {
            return in.nextLine();
        }
        throw new EndOfInputException("End of input");
    }
}
