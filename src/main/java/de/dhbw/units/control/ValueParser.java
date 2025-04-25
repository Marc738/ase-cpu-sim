package de.dhbw.units.control;

import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Value;
import de.dhbw.utils.result.Result;

public class ValueParser {

    public boolean isValue(String param) {
        return param.matches("#[01]{" + Word.WORD_SIZE + "}");
    }

    public Result<Value> getValue(String param) {
        boolean[] value = new boolean[Word.WORD_SIZE];
        char[] paramChars = param.substring(1).toCharArray();

        for (int i = 0; i < value.length; i++) {
            if (paramChars[i] == '1') {
                value[i] = true;
            } else if (paramChars[i] == '0') {
                value[i] = false;
            } else {
                return Result.error(new Exception("Parameter can't be converted to value!"));
            }
        }

        Word word = new Word();
        Result<?> setValueResult = word.setValue(value);
        if (setValueResult instanceof Result.Ok<?>) {
            return Result.ok(new Value(Address.defaultAddress(), word));
        } else {
            return (Result.Error) setValueResult;
        }
    }
}