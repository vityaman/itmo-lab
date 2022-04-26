package ru.vityaman.tidb;

import java.io.PrintWriter;
import java.util.Objects;

import ru.vityaman.tidb.data.resource.exception.ResourceException;
import ru.vityaman.tidb.ui.App;
import ru.vityaman.tidb.ui.input.StreamInput;
import ru.vityaman.tidb.ui.out.StreamOut;


public class Tidb {
    public static void main(String[] args) {
        String filepath = Objects.requireNonNullElseGet(
            System.getenv("TIDB_FILE"), () -> ""
        );

        try {
            App app = new App(
                new StreamOut(System.out),
                new StreamInput(System.in),
                filepath
            );
            
            app.run();
        } catch (ResourceException e) {
            System.err.print(e.getMessage());
        }
    }
}
