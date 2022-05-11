package ru.vityaman.tidb.ui.input;

public class EndOfInputException extends RuntimeException {
    public EndOfInputException(String message) {
        super(message);
    }

    public EndOfInputException(EndOfInputException e) {
        super(e.getMessage(), e);
    }
}
