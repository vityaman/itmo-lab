package ru.vityaman.tidb;

import java.nio.file.Paths;

import ru.vityaman.tidb.data.file.exception.FileSystemException;
import ru.vityaman.tidb.data.file.exception.InvalidFileContentException;
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
                Paths.get(filepath)
            );
            app.run();
        } catch (FileSystemException | InvalidFileContentException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
