package units.alu.subunit.arithmetic;

import de.dhbw.units.alu.subunit.OperatorResult;
import de.dhbw.units.alu.subunit.arithmetic.ArithmeticSubUnit;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArithmeticSubUnitTest {

    @Test
    void testeCanProcessAdd() {
        ArithmeticSubUnit subUnit = new ArithmeticSubUnit();
        Result<?> result = subUnit.canProcess("add");
        assertTrue(result instanceof Result.Ok<?>);
    }

    @Test
    void testeCanProcessSubtract() {
        ArithmeticSubUnit subUnit = new ArithmeticSubUnit();
        Result<?> result = subUnit.canProcess("sub");
        assertTrue(result instanceof Result.Ok<?>);
    }

    @Test
    void testeCannotProcessUnknownKeyword() {
        ArithmeticSubUnit subUnit = new ArithmeticSubUnit();
        Result<?> result = subUnit.canProcess("mul");
        assertTrue(result instanceof Result.Error<?>);
    }

    @Test
    void testeProcessAdd() {
        ArithmeticSubUnit subUnit = new ArithmeticSubUnit();

        Word op1 = new Word();
        Word op2 = new Word();
        op1.setValue(new boolean[]{false, false, false, false, false, false, false, true}); // 1
        op2.setValue(new boolean[]{false, false, false, false, false, false, false, true}); // 1

        Result<OperatorResult> operatorResult = subUnit.process("add", op1, op2);
        assertTrue(operatorResult instanceof Result.Ok<?>);
        boolean[] expected = new boolean[]{false, false, false, false, false, false, true, false};
        assertArrayEquals(expected, ((Result.Ok<OperatorResult>) operatorResult).getValue().getResult().getValue());
    }

    @Test
    void testeProcessSubtract() {
        ArithmeticSubUnit subUnit = new ArithmeticSubUnit();

        Word op1 = new Word();
        Word op2 = new Word();
        op1.setValue(new boolean[]{false, false, false, false, false, false, true, true}); // 3
        op2.setValue(new boolean[]{false, false, false, false, false, false, false, true}); // 1

        Result<OperatorResult> operatorResult = subUnit.process("sub", op1, op2);
        assertTrue(operatorResult instanceof Result.Ok<?>);
        boolean[] expected = new boolean[]{false, false, false, false, false, false, true, false};
        assertArrayEquals(expected, ((Result.Ok<OperatorResult>) operatorResult).getValue().getResult().getValue());
    }

    @Test
    void testProcessUnknownKeyword() {
        ArithmeticSubUnit subUnit = new ArithmeticSubUnit();

        Word op1 = new Word();
        Word op2 = new Word();
        Result<OperatorResult> operatorResult = subUnit.process("mul", op1, op2);
        assertTrue(operatorResult instanceof Result.Error<OperatorResult>);
    }
}