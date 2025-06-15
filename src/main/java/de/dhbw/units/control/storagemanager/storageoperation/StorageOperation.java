package de.dhbw.units.control.storagemanager.storageoperation;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.result.Result;

public interface StorageOperation {

    public boolean canProcess(Instruction instruction);

    public Result<?> process(ProcessingUnit[] units, Word storedValue, Instruction instruction);

}
