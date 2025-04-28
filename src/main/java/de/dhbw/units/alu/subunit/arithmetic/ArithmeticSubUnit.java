package de.dhbw.units.alu.subunit.arithmetic;

import de.dhbw.units.alu.subunit.Operator;
import de.dhbw.units.alu.subunit.OperatorResult;
import de.dhbw.units.alu.subunit.SubUnit;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.result.Result;

public class ArithmeticSubUnit implements SubUnit {
// todo: let only accept instruction with keyword, op1 and op2 should be loaded
    private Operator[] operators;

    public ArithmeticSubUnit() {
        operators = new Operator[]{new AddOperator(), new SubtractOperator()};
    }

    @Override
    public Result<?> canProcess(String keyword) {
        for(Operator operator : operators) {
            if(operator.canProcess(keyword) instanceof Result.Ok<?>) {
                return Result.ok();
            }
        }
        return Result.error(new Exception("No matching operator found!"));
    }

    @Override
    public Result<OperatorResult> process(String keyword, Word op1, Word op2) {
        for(Operator operator : operators) {
            if(operator.canProcess(keyword) instanceof Result.Ok<?>) {
                OperatorResult operatorResult = operator.process(op1, op2);
                return Result.ok(operatorResult);
            }
        }
        return Result.error(new Exception("No matching operator found!"));
    }
}
