package units.control;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.units.control.Command;
import de.dhbw.units.control.Decoder;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DecoderTest {

    @Test
    void testeDecodeWithImmediateValue() {
        Decoder decoder = new Decoder();
        Command command = Command.fromString("sub #00000000");

        Result<Instruction> result = decoder.decode(new ProcessingUnit[0], command);

        assertTrue(result instanceof Result.Ok);
        Instruction instr = ((Result.Ok<Instruction>) result).getValue();
        assertEquals("sub", instr.getKeyword());
        assertEquals(1, instr.getValues().length);
        assertEquals(Word.WORD_SIZE, instr.getValues()[0].getWord().getValue().length);
    }

    @Test
    void testeDecodeWithAddress() {
        ProcessingUnit mockPU = mock(ProcessingUnit.class);
        Address address = new Address("r", 1);
        Word word = new Word();
        word.setValue(new boolean[Word.WORD_SIZE]);
        when(mockPU.read(any(Address.class))).thenReturn(Result.ok(word));

        ProcessingUnit[] units = new ProcessingUnit[]{mockPU};

        Decoder decoder = new Decoder();
        Command command = Command.fromString("add r1");

        Result<Instruction> result = decoder.decode(units, command);

        assertTrue(result instanceof Result.Ok);
        Instruction instr = ((Result.Ok<Instruction>) result).getValue();
        assertEquals("add", instr.getKeyword());
    }

    @Test
    void testDecodeInvalidValue() {
        Decoder decoder = new Decoder();
        Command command = Command.fromString("load #invalid");

        Result<Instruction> result = decoder.decode(new ProcessingUnit[0], command);

        assertTrue(result instanceof Result.Error);
    }

    @Test
    void testDecodeInvalidAddress() {
        Decoder decoder = new Decoder();
        Command command = Command.fromString("load XX"); // Ungültige Adresse

        Result<Instruction> result = decoder.decode(new ProcessingUnit[0], command);

        assertTrue(result instanceof Result.Error);
    }
}

