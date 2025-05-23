package de.dhbw.io.input;

import de.dhbw.units.control.Command;

public class InputConverterImpl implements InputConverter {

    @Override
    public Command toCommand(String input) {
        String[] splits = input.split(" ");
        Command.CommandBuilder commandBuilder = new Command.CommandBuilder();
        commandBuilder.setKeyword(splits[0]);
        for(int i = 1; i < splits.length; i++) {
            commandBuilder.addArgs(splits[i]);
        }
        return commandBuilder.build();
    }

}
