package de.dhbw.units.control;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.result.Result;

public class AddressResolver {
    public Result<InstructionValue> getAddressAndValue(ProcessingUnit[] processingUnits, String param) {
        Result<?> addressResult = Address.parseRawAddress(param);

        if (addressResult instanceof Result.Ok<?> ok && ok.getValue() instanceof Address address) {
            for (ProcessingUnit unit : processingUnits) {
                Result<Word> readResult = unit.read(address);
                if (readResult instanceof Result.Ok<Word> okRead && okRead.getValue() instanceof Word word) {
                    return Result.ok(new InstructionValue(address, word));
                }
            }
            return Result.error(new Exception("No matching address found for parameter!"));
        } else {
            return Result.error(new Exception("Parameter can't be converted to address!"));
        }
    }
}
