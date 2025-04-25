package de.dhbw.units.control;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.result.Result;

public class ControlUnit {

    private final ProcessingUnit[] processingUnits;

    public ControlUnit(ProcessingUnit[] processingUnits) {
        this.processingUnits = processingUnits;
    }

    public Result<?> process(Command command) {
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
