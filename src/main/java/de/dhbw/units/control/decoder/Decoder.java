package de.dhbw.units.control.decoder;

import de.dhbw.utils.data.Command;
import de.dhbw.units.control.decoder.command.CommandDecoder;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.result.Result;

import java.util.ArrayList;

public class Decoder {

    private final ArgDecoder argDecoder;
    private final CommandDecoder[] commandDecoders;

    public Decoder(ArgDecoder argDecoder, CommandDecoder[] commandDecoders) {
        this.argDecoder = argDecoder;
        this.commandDecoders = commandDecoders;
    }

    public Result<Instruction[]> decode(Command command) {
        String keyword = command.getKeyword();
        Result<InstructionValue[]> instructionValuesResult = argDecoder.decode(command.getArgs().toArray(new String[0]));

        if(instructionValuesResult instanceof Result.Error<?> instructionValuesError) {
            return Result.error(instructionValuesError.getException());
        } else if(instructionValuesResult instanceof Result.Ok<InstructionValue[]> instructionValuesOk) {
            InstructionValue[] instructionValues = instructionValuesOk.getValue();

            ArrayList<Instruction> instructions = new ArrayList<>();

            for (CommandDecoder commandDecoder : commandDecoders) {
                Result<Instruction[]> commandDecodeResult = commandDecoder.decode(keyword, instructionValues);
                if(commandDecodeResult instanceof Result.Ok<Instruction[]> commandDecodeOk) {
                    return commandDecodeOk;
                }
            }
        }
        return Result.error(new Exception("Command could not be decoded!"));
    }
}
