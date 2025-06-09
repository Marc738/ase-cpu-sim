package units.register;

import de.dhbw.units.register.Register;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.types.SetInstruction;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class RegisterTest {

    @Test
    void testReadAndWrite() {
        Register register = new Register(4);

        Address address = new Address("r", 2);
        Word word = new Word();
        word.setValue(new boolean[]{true, false, true, false, true, false, true, false});

        // Schreiben
        Result<?> writeResult = register.write(address, word);
        assertInstanceOf(Result.Ok.class, writeResult);

        // Lesen
        Result<Word> readResult = register.read(address);
        assertInstanceOf(Result.Ok.class, readResult);

        Word readWord = ((Result.Ok<Word>) readResult).getValue();
        assertArrayEquals(word.getValue(), readWord.getValue());
    }

    @Test
    void testReadNonExistingAddress() {
        Register register = new Register(2);

        Address invalidAddress = new Address("r", 5);
        Result<Word> readResult = register.read(invalidAddress);

        assertInstanceOf(Result.Error.class, readResult);
    }

    @Test
    void testWriteNonExistingAddress() {
        Register register = new Register(2);

        Address invalidAddress = new Address("r", 5);
        Word word = new Word();
        Result<?> writeResult = register.write(invalidAddress, word);

        assertInstanceOf(Result.Error.class, writeResult);
    }

    @Test
    void testCanNotProcess() {
        Register register = new Register(2);
        Result<?> canProcessResult = register.canProcess("set");

        assertInstanceOf(Result.Error.class, canProcessResult);
    }

    @Test
    void testProcessReturnsError() {
        Register register = new Register(2);
        Instruction instruction = new SetInstruction(new Address("r", 0));
        Result<?> canProcessResult = register.process(instruction);

        assertInstanceOf(Result.Error.class, canProcessResult);
    }
}
