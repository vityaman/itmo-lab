package ru.vityaman.tidb;

import ru.vityaman.tidb.data.file.exception.FileAccessException;
import ru.vityaman.tidb.data.file.exception.InvalidFileStructureException;
import ru.vityaman.tidb.data.resource.exception.ResourceException;
import ru.vityaman.tidb.ui.App;
import ru.vityaman.tidb.ui.input.StreamInput;
import ru.vityaman.tidb.ui.out.StreamOut;


public class Tidb {
    public static void main(String[] args) {
        String filepath = System.getenv("TIDB_FILE");
        if (filepath == null) {
            filepath = "untitled.json";
        }

        try {
            App app = new App(
                new StreamOut(System.out),
                new StreamInput(System.in),
                filepath
            );
            app.run();
        } catch (ResourceException
                | FileAccessException
                | InvalidFileStructureException e) {
            System.err.println(e.getMessage());
        }
    }
}
