package de.dhbw.utils.instruction.types;

import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;

public class UnitInstruction extends Instruction {
    public UnitInstruction(String keyword) {
        super(keyword, new InstructionValue[]{});
    }
}
