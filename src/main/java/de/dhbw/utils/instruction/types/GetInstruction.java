package de.dhbw.utils.instruction.types;

import de.dhbw.utils.address.Address;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;

public class GetInstruction extends Instruction {
    public GetInstruction(Address targetAddress) {
        super("get", new InstructionValue[]{new InstructionValue(targetAddress, null)});
    }
}
