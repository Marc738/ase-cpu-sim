package de.dhbw.io.input;

import java.util.Scanner;

public class InputHandlerImpl implements InputHandler {

    private Scanner keyboard;

    public InputHandlerImpl() {
        keyboard = new Scanner(System.in);
    }

    @Override
    public String read() {
        if (keyboard.hasNextLine()) {
            return keyboard.nextLine();
        } else {
            return ""; // oder Fehler behandeln
        }
    }
}
