package de.dhbw.units.alu;

import de.dhbw.exceptions.UnexpectedResultException;
import de.dhbw.units.ProcessingUnit;
import de.dhbw.units.alu.subunit.OperatorResult;
import de.dhbw.units.alu.subunit.SubUnit;
import de.dhbw.units.alu.subunit.arithmetic.ArithmeticSubUnit;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.StorageSpace;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.result.Result;

public class ALU implements ProcessingUnit {

    public static final Address OP1 = new Address("op", 1);
    public static final Address OP2 = new Address("op", 2);
    public static final Address RES = new Address("res", 0);

    private StorageSpace op1;
    private StorageSpace op2;
    private StorageSpace res0;

    private SubUnit[] subUnits;

    public ALU() {
        initStorageSpaces();
        initSubUnits();
    }

    private void initStorageSpaces() {
        op1 = new StorageSpace(OP1);
        op2 = new StorageSpace(OP2);
        res0 = new StorageSpace(RES);
    }

    private void initSubUnits() {
        subUnits = new SubUnit[]{new ArithmeticSubUnit()};
    }

    @Override
    public Result<?> canProcess(String keyword) {
        for(SubUnit subUnit : subUnits) {
            Result<?> canProcessResult = subUnit.canProcess(keyword);
            if(canProcessResult instanceof Result.Ok<?>) {
                return canProcessResult;
            }
        }
        return Result.error(new Exception("Can not process keyword"));
    }

    @Override
    public Result<?> process(Instruction instruction) {
        for(SubUnit subUnit : subUnits) {
            Result<?> canProcessResult = subUnit.canProcess(instruction.getKeyword());
            if(canProcessResult instanceof Result.Ok<?>) {
                Result<OperatorResult> subUnitProcessResult = subUnit.process(instruction.getKeyword(), op1.getWord(), op2.getWord());
                if(subUnitProcessResult instanceof Result.Ok<OperatorResult> subUnitProcessOk) {
                    res0.setWord(subUnitProcessOk.getValue().getResult());
                    return Result.ok();
                } else {
                    return subUnitProcessResult;
                }
            }
        }
        return Result.error(new Exception("Can not process keyword"));
    }

    @Override
    public Result<Word> read(Address address) {
        Result<StorageSpace> findStorageSpaceResult = findStorageSpace(address);
        if(findStorageSpaceResult instanceof Result.Ok<StorageSpace> findStorageSpaceOk) {
            Word word = findStorageSpaceOk.getValue().getWord();
            return Result.ok(word);
        } else if(findStorageSpaceResult instanceof Result.Error<StorageSpace> findStorageSpaceError){
            return Result.error(findStorageSpaceError.getException());
        } else {
            return Result.error(new UnexpectedResultException(this.getClass().getSimpleName()));
        }
    }

    @Override
    public Result<?> write(Address address, Word word) {
        Result<StorageSpace> findStorageSpaceResult = findStorageSpace(address);
        if(findStorageSpaceResult instanceof Result.Ok<StorageSpace> findStorageSpaceOk) {
            findStorageSpaceOk.getValue().setWord(word);
            return Result.ok();
        } else if(findStorageSpaceResult instanceof Result.Error<StorageSpace> findStorageSpaceError){
            return Result.error(findStorageSpaceError.getException());
        } else {
            return Result.error(new UnexpectedResultException(this.getClass().getSimpleName()));
        }
    }

    private Result<SubUnit> findSubUnitByKeyword(String keyword) {
        for(SubUnit subUnit : subUnits) {
            Result<?> canProcessResult = subUnit.canProcess(keyword);
            if(canProcessResult instanceof Result.Ok<?> canProcessOk) {
                return Result.ok(subUnit);
            }
        }
        return Result.error(new Exception("No matching subunit found!"));
    }

    private Result<StorageSpace> findStorageSpace(Address address) {
        if(op1.getAddress().equals(address)) {
            return Result.ok(op1);
        } else if(op2.getAddress().equals(address)) {
            return Result.ok(op2);
        } else if(res0.getAddress().equals(address)) {
            return Result.ok(res0);
        }
        return Result.error(new Exception("No matching storagespace found!"));
    }
}
