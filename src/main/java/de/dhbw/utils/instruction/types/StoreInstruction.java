package de.dhbw.utils.instruction.types;

import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;

public class StoreInstruction extends Instruction {
    public StoreInstruction(Word word) {
        super("store", new InstructionValue[]{new InstructionValue(null, word)});
    }
}
