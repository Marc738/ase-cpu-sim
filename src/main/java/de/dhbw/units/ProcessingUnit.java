package de.dhbw.units;

import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.result.Result;

public interface ProcessingUnit {
    Result<?> canProcess(String keyword);
    Result<?> process(Instruction instruction);

    Result<Word> read(Address address);
    Result<?> write(Address address, Word word);
}
