package de.dhbw.io.input;

import de.dhbw.units.control.Command;

public interface InputConverter {

    public Command toCommand(String input);

}
