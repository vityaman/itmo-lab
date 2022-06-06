package ru.vityaman.vion.exception;

public class ConversionException extends Exception {
    public ConversionException(Throwable cause) {
        super("Can't convert to vion: " + cause.getMessage(), cause);
    }
}
