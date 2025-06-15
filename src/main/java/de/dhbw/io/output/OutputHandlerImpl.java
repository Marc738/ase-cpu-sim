package de.dhbw.io.output;

import de.dhbw.utils.data.Word;
import de.dhbw.utils.result.Result;

import java.io.PrintStream;

public class OutputHandlerImpl implements OutputHandler {

    private PrintStream printStream;

    public OutputHandlerImpl() {
        printStream = System.out;
    }

    @Override
    public void print(String text) {
        printStream.println(text);
    }
}
