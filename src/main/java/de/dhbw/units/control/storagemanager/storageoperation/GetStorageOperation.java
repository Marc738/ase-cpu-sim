package de.dhbw.units.control.storagemanager.storageoperation;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.result.Result;

public class GetStorageOperation implements StorageOperation {

    private final String key = "get";

    @Override
    public boolean canProcess(Instruction instruction) {
        return instruction.getKeyword().contentEquals(key) && instruction.getValues().length == 1 && instruction.getValues()[0].getAddress() != null;
    }

    @Override
    public Result<?> process(ProcessingUnit[] units, Word storedValue, Instruction instruction) {
        if(!canProcess(instruction)) {
            return Result.error(new Exception("Can't process instruction!"));
        }
        for(ProcessingUnit processingUnit : units) {
            Result<Word> result = processingUnit.read(instruction.getValues()[0].getAddress());
            if(result instanceof Result.Ok<Word> resultOk) {
                return Result.ok(resultOk.getValue());
            }
        }
        return Result.error(new Exception("Address could not be matched with any given ProcessingUnit!"));
    }

}
