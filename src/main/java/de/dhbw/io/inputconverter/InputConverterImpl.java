package de.dhbw.io.inputconverter;

import de.dhbw.utils.data.Command;

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
