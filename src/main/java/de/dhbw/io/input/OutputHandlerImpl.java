package de.dhbw.io.input;

import de.dhbw.utils.data.Word;
import de.dhbw.utils.result.Result;

import java.io.PrintStream;

public class OutputHandlerImpl implements OutputHandler {

    private PrintStream printStream;
    public static final String REST_COLOR = "\u001B[0m";
    public static final String OK_COLOR = "\u001B[32m";
    public static final String ERROR_COLOR = "\u001B[33m";
    public static final String FATAL_ERROR_COLOR = "\u001B[31m";

    public OutputHandlerImpl() {
        printStream = System.out;
    }

    @Override
    public void print(Result<?> result) {
        if(result instanceof Result.Ok<?> ok) {
            print("Command wurde ohne Fehler ausgeführt!", OK_COLOR);
            if(ok.hasValue() && ok.getValue() instanceof Word word) {
                print("Neuer Wert in StoredValue: " + word.toString(), OK_COLOR);
            }
        } else if(result instanceof Result.Error<?> error) {
            print(error.getException().getMessage(), ERROR_COLOR);
        } else {
            print("FATAL ERROR: Command hat unerwarteten Fehler ausgelöst", FATAL_ERROR_COLOR);
        }
    }

    private void print(String text, String colorCode) {
        printStream.println(colorCode + text + REST_COLOR);
    }
}
