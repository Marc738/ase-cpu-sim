package de.dhbw.units.control.decoder.command;

import de.dhbw.utils.address.Address;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.instruction.types.GetInstruction;
import de.dhbw.utils.result.Result;

import java.util.ArrayList;

public class GetCommandDecoder extends CommandDecoder {

    public GetCommandDecoder() {
        super("get");
    }

    @Override
    public Result<Instruction[]> decode(String keyword, InstructionValue[] instructionValues) {
        if (!canDecodeCommand(keyword, instructionValues)) {
            return Result.error(new Exception("Can not decode command!"));
        }
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();

        // Erstes Arg
        InstructionValue instructionValue1 = instructionValues[0];
        Address targetAddress = instructionValue1.getAddress();
        if (targetAddress != null) {
            instructions.add(new GetInstruction(targetAddress));
        } else {
            return Result.error(new Exception("Invalid decoding of arg!"));
        }

        return Result.ok(instructions.toArray(Instruction[]::new));
    }

    public boolean canDecodeCommand(String keyword, InstructionValue[] instructionValues) {
        return this.keyword.contentEquals(keyword) && instructionValues.length == 1 && instructionValues[0].getAddress() != null;
    }
}
