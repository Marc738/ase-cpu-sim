package units.alu.subunit.arithmetic;

import de.dhbw.units.alu.subunit.OperatorResult;
import de.dhbw.units.alu.subunit.arithmetic.AddOperator;
import de.dhbw.utils.data.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddOperatorTest {

    @Test
    void testeEinfacheAddition() {
        Word op1 = new Word();
        Word op2 = new Word();

        op1.setValue(new boolean[]{false, false, false, false, false, false, false, true}); // 1
        op2.setValue(new boolean[]{false, false, false, true, false, false, false, false}); // 2

        AddOperator addOperator = new AddOperator();
        OperatorResult result = addOperator.process(op1, op2);

        boolean[] expected = new boolean[]{false, false, false, true, false, false, false, true}; // 3
        assertArrayEquals(expected, result.getResult().getValue());
    }

    @Test
    void testeAdditionMitÜberlauf() {
        Word op1 = new Word();
        Word op2 = new Word();

        op1.setValue(new boolean[]{false, false, false, false, false, false, false, true}); // 1
        op2.setValue(new boolean[]{true, true, true,  true, true, true, true,  true}); // 15

        AddOperator addOperator = new AddOperator();
        OperatorResult result = addOperator.process(op1, op2);

        boolean[] expected = new boolean[]{false, false, false, false, false, false, false, false}; // 3
        assertArrayEquals(expected, result.getResult().getValue());
    }
}