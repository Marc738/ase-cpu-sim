package de.dhbw.units.control.decoder;

import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.result.Result;

import java.util.ArrayList;

public class ArgDecoder {

    public Result<InstructionValue[]> decode(String[] args) {
        ArrayList<InstructionValue> instructionValues = new ArrayList<>();
        for(String arg : args) {
            Result<Word> wordResult = Word.fromBinaryString(arg);
            if(wordResult instanceof Result.Ok<Word> wordOk) {
                instructionValues.add(new InstructionValue(null, wordOk.getValue()));
                continue;
            }
            Result<Address> addressResult = Address.parseRawAddress(arg);
            if(addressResult instanceof Result.Ok<Address> addressOk) {
                instructionValues.add(new InstructionValue(addressOk.getValue(), null));
                continue;
            }
            return Result.error(new Exception("Args could not be decoded!"));
        }
        return Result.ok(instructionValues.toArray(InstructionValue[]::new));
    }

}
