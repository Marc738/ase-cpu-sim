package de.dhbw.units.control;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.result.Result;

public class ControlUnit {

    private final ProcessingUnit[] processingUnits;
    private final Decoder decoder;
    private final StorageManager storageManager;

    public ControlUnit(ProcessingUnit[] processingUnits, Decoder decoder, StorageManager storageManager) {
        this.processingUnits = processingUnits;
        this.decoder = decoder;
        this.storageManager = storageManager;
    }

    public Result<?> process(Command command) {
        // Decode Command to Instruction[]
        // mv r1 r2 => {get r1, set r2 [valueOfR1]}
        decoder.decode(processingUnits, command);

        /
        Result<?> matchingProcessingUnitResult = findMatchingProcessingUnit(command);
        if(matchingProcessingUnitResult instanceof Result.Error<?> error) {
            return error;
        }
        return Result.error(new Exception("Not implemented!"));
    }

    private Result<?> findMatchingProcessingUnit(Command command) {
        for(ProcessingUnit processingUnit : processingUnits) {
            Result<?> canProcessResult = processingUnit.canProcess(command.getKeyword());
            if(canProcessResult instanceof Result.Ok<?>) {
                return Result.ok(processingUnit);
            }
        }
        return Result.error(new Exception("No matching processing unit!"));
    }

    private Result<?> createInstruction(Command command) {
        return Result.error(new Exception("Not implemented"));
    }

}
