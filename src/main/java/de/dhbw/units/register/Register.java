package de.dhbw.units.register;

import de.dhbw.exceptions.NoMatchingAddressFoundException;
import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.StorageSpace;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.result.Result;

public class Register implements ProcessingUnit {

    private StorageSpace[] storageSpaces;

    public Register(int amountBytes) {
        StorageSpace.StorageSpacesBuilder storageSpacesBuilder = new StorageSpace.StorageSpacesBuilder();
        storageSpacesBuilder.addCount("r", amountBytes);
        storageSpaces = storageSpacesBuilder.build();
    }

    @Override
    public Result<?> canProcess(String keyword) {
        return Result.error(new Exception("Can not process keyword"));
    }

    @Override
    public Result<?> process(Instruction instruction) {
        return Result.error(new Exception("Can not process instruction"));
    }

    @Override
    public Result<Word> read(Address address) {
        for(StorageSpace storageSpace : storageSpaces) {
            if(storageSpace.getAddress().equals(address)) {
                return Result.ok(storageSpace.getWord());
            }
        }
        return Result.error(new NoMatchingAddressFoundException("Address was not found!"));
    }

    @Override
    public Result<?> write(Address address, Word word) {
        for(StorageSpace storageSpace : storageSpaces) {
            if(storageSpace.getAddress().equals(address)) {
                storageSpace.setWord(word);
                return Result.ok();
            }
        }
        return Result.error(new NoMatchingAddressFoundException("Address was not found!"));
    }

}
