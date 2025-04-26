package de.dhbw.units.control;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.Value;
import de.dhbw.utils.result.Result;

public class StorageManager {

    private static final String KEYWORD = "mv";

    public Result<?> canProcess(Instruction instruction) {
        if(instruction.getKeyword().contentEquals(KEYWORD)) {
            Value[] values = instruction.getValues();
            if (values.length == 2) {
                if(values[0].getAddress() != null && values[1].getAddress() != null) {
                    return Result.ok();
                } else {
                    return Result.error(new Exception("Not all arguments are addresses"));
                }
            } else {
                return Result.error(new Exception("Number of values don't match"));
            }
        } else {
            return Result.error(new Exception("Keyword mismatching"));
        }
    }

    public Result<?> process(ProcessingUnit[] units, Instruction instruction) {
        Result<?> canProcessResult = canProcess(instruction);
        if(canProcessResult instanceof Result.Error<?>) {
            return canProcessResult;
        }
        Value[] values = instruction.getValues();
        Address targetAddress = values[0].getAddress();
        Word targetWord = values[1].getWord();
        Result<ProcessingUnit> findUnitResult = findUnitWithMatchingAddress(units, targetAddress);
        if(findUnitResult instanceof Result.Ok<ProcessingUnit> finUnitOk) {
            ProcessingUnit unit = finUnitOk.getValue();
            Result<?> writeUnitResult = unit.write(targetAddress, targetWord);
            return writeUnitResult;
        } else {
            return findUnitResult;
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
