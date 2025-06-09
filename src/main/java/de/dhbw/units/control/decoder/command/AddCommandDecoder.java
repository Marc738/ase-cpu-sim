package de.dhbw.units.control.decoder.command;

import de.dhbw.units.alu.ALU;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.instruction.types.GetInstruction;
import de.dhbw.utils.instruction.types.SetInstruction;
import de.dhbw.utils.instruction.types.StoreInstruction;
import de.dhbw.utils.instruction.types.UnitInstruction;
import de.dhbw.utils.result.Result;

import java.util.ArrayList;

public class AddCommandDecoder extends CommandDecoder {

    public AddCommandDecoder() {
        super("add");
    }

    @Override
    public Result<Instruction[]> decode(String keyword, InstructionValue[] instructionValues) {
        if (!canDecodeCommand(keyword, instructionValues)) {
            return Result.error(new Exception("Can not decode command!"));
        }
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();

        // Erstes Arg
        InstructionValue instructionValue1 = instructionValues[0];
        Address address = instructionValue1.getAddress();
        Word word = instructionValue1.getWord();
        if (address != null && word == null) { // Erstes Arg ist Address
            instructions.add(new GetInstruction(address));
            instructions.add(new SetInstruction(ALU.OP1));
        } else if (address == null && word != null) { // Erstes Arg ist Word
            instructions.add(new StoreInstruction(word));
            instructions.add(new SetInstruction(ALU.OP1));
        } else {
            return Result.error(new Exception("Invalid decoding of arg!"));
        }

        // Zweites Arg
        InstructionValue instructionValue2 = instructionValues[1];
        address = instructionValue2.getAddress();
        word = instructionValue2.getWord();
        if (address != null && word == null) { // Zweites Arg ist Address
            instructions.add(new GetInstruction(address));
            instructions.add(new SetInstruction(ALU.OP2));
        } else if (address == null && word != null) { // Zweites Arg ist Word
            instructions.add(new StoreInstruction(word));
            instructions.add(new SetInstruction(ALU.OP2));
        } else {
            return Result.error(new Exception("Invalid decoding of arg!"));
        }

        // Ausführende Instruction
        instructions.add(new UnitInstruction(this.keyword));
        return Result.ok(instructions.toArray(Instruction[]::new));
    }

    public boolean canDecodeCommand(String keyword, InstructionValue[] instructionValues) {
        return this.keyword.contentEquals(keyword) && instructionValues.length == 2;
    }
}
