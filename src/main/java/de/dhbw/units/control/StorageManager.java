package de.dhbw.units.control;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.result.Result;

public class StorageManager {

    private static final String SET = "set";
    private static final String GET = "get";
    private static final String STORE = "store";

    public Result<?> canProcess(Instruction instruction) {
        if(instruction.getKeyword().contentEquals(SET)) {
            InstructionValue[] instructionValues = instruction.getValues();
            if (instructionValues.length == 1) {
                if(instructionValues[0].getAddress() != null) {
                    return Result.ok();
                } else {
                    return Result.error(new Exception("The first arg must be an address"));
                }
            } else {
                return Result.error(new Exception("Number of args don't match"));
            }
        } else if(instruction.getKeyword().contentEquals(GET)) {
            InstructionValue[] instructionValues = instruction.getValues();
            if (instructionValues.length == 1) {
                if(instructionValues[0].getAddress() != null) {
                    return Result.ok();
                } else {
                    return Result.error(new Exception("The arg must be an address"));
                }
            } else {
                return Result.error(new Exception("Number of args don't match"));
            }
        } else if(instruction.getKeyword().contentEquals(STORE)) {
            InstructionValue[] instructionValues = instruction.getValues();
            if (instructionValues.length == 1) {
                if(instructionValues[0].getWord() != null) {
                    return Result.ok();
                } else {
                    return Result.error(new Exception("The arg must be an address"));
                }
            } else {
                return Result.error(new Exception("Number of args don't match"));
            }
        } else {
            return Result.error(new Exception("Keyword mismatching"));
        }
    }

    public Result<?> process(ProcessingUnit[] units, Word storedValue, Instruction instruction) {
        Result<?> canProcessResult = canProcess(instruction);
        if(canProcessResult instanceof Result.Error<?>) {
            return canProcessResult;
        }
        if(instruction.getKeyword().contentEquals(SET)) {
            InstructionValue[] instructionValues = instruction.getValues();
            Address targetAddress = instructionValues[0].getAddress();
            Result<ProcessingUnit> findUnitResult = findUnitWithMatchingAddress(units, targetAddress);
            if(findUnitResult instanceof Result.Ok<ProcessingUnit> findUnitOk) {
                ProcessingUnit unit = findUnitOk.getValue();
                Result<?> writeUnitResult = unit.write(targetAddress, storedValue);
                return writeUnitResult;
            } else {
                return findUnitResult;
            }
        } else if(instruction.getKeyword().contentEquals(GET)) {
            InstructionValue[] instructionValues = instruction.getValues();
            Address targetAddress = instructionValues[0].getAddress();
            Result<ProcessingUnit> findUnitResult = findUnitWithMatchingAddress(units, targetAddress);
            if(findUnitResult instanceof Result.Ok<ProcessingUnit> findUnitOk) {
                ProcessingUnit unit = findUnitOk.getValue();
                Result<Word> readUnitResult = unit.read(targetAddress);
                if(readUnitResult instanceof Result.Ok<Word> wordResult) {
                    storedValue.setValue(wordResult.getValue().getValue());
                }
                return readUnitResult;
            } else {
                return findUnitResult;
            }
        } else if(instruction.getKeyword().contentEquals(STORE)) {
            InstructionValue[] instructionValues = instruction.getValues();
            Word word = instructionValues[0].getWord();
            storedValue.setValue(word.getValue());
            return Result.ok();
        } else {
            return Result.error(new Exception("Keyword mismatching"));
        }
    }

    private Result<ProcessingUnit> findUnitWithMatchingAddress(ProcessingUnit[] units, Address address) {
        for(ProcessingUnit unit : units) {
            Result<?> readUnitAddressResult = unit.read(address);
            if(readUnitAddressResult instanceof Result.Ok<?>) {
                return Result.ok(unit);
            }
        }
        return Result.error(new Exception("No matching Unit found for Address!"));
    }
}
