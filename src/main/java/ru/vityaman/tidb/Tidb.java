package ru.vityaman.tidb;

import java.nio.file.Paths;

import ru.vityaman.tidb.file.exception.FileSystemException;
import ru.vityaman.tidb.file.exception.InvalidFileContentException;
import ru.vityaman.tidb.ui.App;
import ru.vityaman.tidb.ui.input.StreamInput;
import ru.vityaman.tidb.ui.out.StreamOut;


public class Tidb {
    public static void main(String[] args) {
        String filepath = System.getenv("tidb");
        if (filepath == null) {
            filepath = "untitled.json";
        }

        try {
            App app = new App(
                new StreamOut(System.out),
                new StreamInput(System.in),
                Paths.get(filepath)
            );
            app.run();
        } catch (FileSystemException | InvalidFileContentException e) {
            System.err.println(filepath + ": " + e.getMessage());
        }
    }
}
