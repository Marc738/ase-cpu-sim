package units.alu.subunit.arithmetic;


import de.dhbw.units.alu.subunit.OperatorResult;
import de.dhbw.units.alu.subunit.arithmetic.SubtractOperator;
import de.dhbw.utils.data.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SubtractOperatorTest {

    @Test
    void testeEinfacheSubtraktion() {
        Word op1 = new Word();
        Word op2 = new Word();

        op1.setValue(new boolean[]{false, false, false, false, false, false, true, true}); // 3
        op2.setValue(new boolean[]{false, false, false, false, false, false, false, true}); // 1

        SubtractOperator subtractOperator = new SubtractOperator();
        OperatorResult result = subtractOperator.process(op1, op2);

        boolean[] expected = new boolean[]{false, false, false, false, false, false, true, false}; // 2
        assertArrayEquals(expected, result.getResult().getValue());
    }

    @Test
    void testeSubtraktionMitBorrow() {
        Word op1 = new Word();
        Word op2 = new Word();

        op1.setValue(new boolean[]{false, false, false, false, false, false, false, false}); // 0
        op2.setValue(new boolean[]{false, false, false, false, false, false, false, true}); // 1

        SubtractOperator subtractOperator = new SubtractOperator();
        OperatorResult result = subtractOperator.process(op1, op2);

        boolean[] expected = new boolean[]{true, true, true, true, true, true, true, true}; // -1 als 2er-Komplement
        assertArrayEquals(expected, result.getResult().getValue());
    }
}
