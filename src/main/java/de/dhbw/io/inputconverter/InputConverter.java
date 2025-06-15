package de.dhbw.io.inputconverter;

import de.dhbw.utils.data.Command;

public interface InputConverter {

    public Command toCommand(String input);

}
