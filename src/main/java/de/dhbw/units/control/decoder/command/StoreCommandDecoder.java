package de.dhbw.units.control.decoder.command;

import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.instruction.types.SetInstruction;
import de.dhbw.utils.instruction.types.StoreInstruction;
import de.dhbw.utils.result.Result;

import java.util.ArrayList;

public class StoreCommandDecoder extends CommandDecoder {

    public StoreCommandDecoder() {
        super("store");
    }

    @Override
    public Result<Instruction[]> decode(String keyword, InstructionValue[] instructionValues) {
        if (!canDecodeCommand(keyword, instructionValues)) {
            return Result.error(new Exception("Can not decode command!"));
        }
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();

        InstructionValue instructionValue1 = instructionValues[0];
        Word word = instructionValue1.getWord();
        if (word != null) {
            instructions.add(new StoreInstruction(word));
        } else {
            return Result.error(new Exception("Invalid decoding of arg!"));
        }

        return Result.ok(instructions.toArray(Instruction[]::new));
    }

    public boolean canDecodeCommand(String keyword, InstructionValue[] instructionValues) {
        return this.keyword.contentEquals(keyword) && instructionValues.length == 1 && instructionValues[1].getWord() != null;
    }
}
