package de.dhbw.units.alu.subunit.arithmetic;

import de.dhbw.units.alu.subunit.Operator;
import de.dhbw.units.alu.subunit.OperatorResult;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.result.Result;

import static de.dhbw.utils.data.Word.WORD_SIZE;

public class SubtractOperator extends Operator {

    public SubtractOperator() {
        super("sub");
    }

    @Override
    public OperatorResult process(Word op1, Word op2) {
        boolean[] result = new boolean[WORD_SIZE];
        boolean borrow = false;

        for (int i = WORD_SIZE - 1; i >= 0; i--) {
            boolean bit1 = op1.getValue()[i];
            boolean bit2 = op2.getValue()[i];

            result[i] = bit1 ^ bit2 ^ borrow;
            borrow = (!bit1 && (bit2 || borrow)) || (bit2 && borrow);
        }

        Word wordResult = new Word();
        wordResult.setValue(result);
        return new OperatorResult(wordResult);
    }
}