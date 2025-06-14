package de.dhbw.units.control.storagemanager.storageoperation;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.result.Result;

public class StoreStorageOperation implements StorageOperation {

    private final String key = "store";

    @Override
    public boolean canProcess(Instruction instruction) {
        return instruction.getKeyword().contentEquals(key) && instruction.getValues().length == 1 && instruction.getValues()[0].getWord() != null;
    }

    @Override
    public Result<?> process(ProcessingUnit[] units, Word storedValue, Instruction instruction) {
        if(!canProcess(instruction)) {
            return Result.error(new Exception("Can't process instruction!"));
        }
        storedValue.setValue(instruction.getValues()[0].getWord().getValue());
        return Result.ok();
    }

}
