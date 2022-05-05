package ru.vityaman.tidb.lang.json.exception;

public class JsonConversionException extends Exception {

    public JsonConversionException(Throwable cause) {
        super("Can't convert to json: " + cause.getMessage(), cause);
    }
    
}
