package de.dhbw.units.control;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.units.control.decoder.Decoder;
import de.dhbw.utils.data.Command;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.result.Result;

public class ControlUnit {

    private final ProcessingUnit[] processingUnits;
    private final Decoder decoder;
    private final StorageManager storageManager;

    private final Word storedValue;

    public ControlUnit(ProcessingUnit[] processingUnits, Decoder decoder, StorageManager storageManager, Word storedValue) {
        this.processingUnits = processingUnits;
        this.decoder = decoder;
        this.storageManager = storageManager;
        this.storedValue = storedValue;
    }

    public Result<?> process(Command command) {
        Result<Instruction[]> instructionResult = decoder.decode(command);
        if(instructionResult instanceof Result.Ok<Instruction[]> instructionOk) {
            Instruction[] instructions = instructionOk.getValue();
            return processInstructions(instructions);
        } else {
            return instructionResult;
        }
    }

    private Result<?> processInstructions(Instruction[] instructions) {
        for(Instruction instruction : instructions) {
            Result<?> canProcessResult = storageManager.canProcess(instruction);
            if(canProcessResult instanceof Result.Ok<?> canProcessOk) {
                // StorageManager
                Result storageManagerResult = processInstructionInStorageManager(instruction);
                if(storageManagerResult instanceof Result.Ok<?> storageManagerOk && storageManagerOk.getValue() instanceof Word word) {
                    storedValue.setValue(word.getValue());
                } else if(storageManagerResult instanceof Result.Error<?> storageManagerError) {
                    return storageManagerError;
                }
            } else {
                // Units
                Result processingInstructionResult = processInstructionInProcessingUnit(instruction);
                if(processingInstructionResult instanceof Result.Error<?> storageManagerError) {
                    return storageManagerError;
                }
            }
        }
        return Result.ok(storedValue);
    }

    private Result processInstructionInStorageManager(Instruction instruction) {
        Result processResult = storageManager.process(processingUnits, storedValue, instruction);
        return processResult;
    }

    private Result<?> processInstructionInProcessingUnit(Instruction instruction) {
        Result<ProcessingUnit> processingUnitResult = findMatchingProcessingUnit(instruction);
        if(processingUnitResult instanceof Result.Ok<ProcessingUnit> processingUnitOk) {
            ProcessingUnit unit = processingUnitOk.getValue();
            Result<?> processResult = unit.process(instruction);
            return processResult;
        } else {
            return processingUnitResult;
        }
    }

    private Result<ProcessingUnit> findMatchingProcessingUnit(Instruction instruction) {
        for(ProcessingUnit processingUnit : processingUnits) {
            Result<?> canProcessResult = processingUnit.canProcess(instruction.getKeyword());
            if(canProcessResult instanceof Result.Ok<?>) {
                return Result.ok(processingUnit);
            }
        }
        return Result.error(new Exception("No matching processing unit!"));
    }

}
