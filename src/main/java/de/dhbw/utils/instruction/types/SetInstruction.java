package de.dhbw.utils.instruction.types;

import de.dhbw.utils.address.Address;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;

public class SetInstruction extends Instruction {
    public SetInstruction(Address targetAddress) {
        super("set", new InstructionValue[]{new InstructionValue(targetAddress, null)});
    }
}
