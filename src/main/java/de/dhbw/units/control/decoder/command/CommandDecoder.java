package de.dhbw.units.control.decoder.command;

import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.result.Result;

public abstract class CommandDecoder {

    protected final String keyword;

    public CommandDecoder(String keyword) {
        this.keyword = keyword;
    }

    public abstract Result<Instruction[]> decode(String keyword, InstructionValue[] instructionValues);

}
