package de.dhbw.units.control;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.units.control.decoder.Decoder;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.result.Result;

public class ControlUnit {

    private final ProcessingUnit[] processingUnits;
    private final Decoder decoder;
    private final StorageManager storageManager;

    private Word storedValue;

    public ControlUnit(ProcessingUnit[] processingUnits, Decoder decoder, StorageManager storageManager) {
        this.processingUnits = processingUnits;
        this.decoder = decoder;
        this.storageManager = storageManager;
        storedValue = new Word();
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
                    storedValue = word;
                } else if(storageManagerResult instanceof Result.Error<?> storageManagerError) {
                    return storageManagerError;
                }
            } else {
                // Units
                processInstructionInProcessingUnit(instruction);
            }
        }
        return Result.ok();
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

    private Result<?> createInstruction(Command command) {
        return Result.error(new Exception("Not implemented"));
    }

}
