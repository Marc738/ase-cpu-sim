package de.dhbw.units.control;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.Value;
import de.dhbw.utils.result.Result;

public class Decoder {

    private final ValueParser valueParser;
    private final AddressResolver addressResolver;

    public Decoder() {
        valueParser = new ValueParser();
        addressResolver = new AddressResolver();
    }

    public Result<Instruction> decode(ProcessingUnit[] storageUnits, Command command) {
        String keyword = command.getKeyword();
        Value[] values = new Value[command.getParams().size()];

        for (int i = 0; i < values.length; i++) {
            String param = command.getParams().get(i);
            if (valueParser.isValue(param)) {
                Result<Value> valueResult = valueParser.getValue(param);
                if (valueResult instanceof Result.Ok<Value> ok) {
                    values[i] = ok.getValue();
                } else {
                    return (Result.Error) valueResult;
                }
            } else {
                Result<Value> addressResult = addressResolver.getAddressAndValue(storageUnits, param);
                if (addressResult instanceof Result.Ok<Value> ok) {
                    values[i] = ok.getValue();
                } else {
                    return (Result.Error) addressResult;
                }
            }
        }
        return Result.ok(new Instruction(keyword, values));
    }
}
