package units.control.decoder;

import de.dhbw.units.control.decoder.ArgDecoder;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgDecoderTest {

    @Test
    public void testDecodeWordAndAddress() {
        ArgDecoder decoder = new ArgDecoder();
        String[] args = {"#00001111", "r2"}; // Word + Address

        Result<InstructionValue[]> result = decoder.decode(args);
        assertTrue(result instanceof Result.Ok);

        InstructionValue[] values = ((Result.Ok<InstructionValue[]>) result).getValue();
        assertEquals(2, values.length);
        assertNotNull(values[0].getWord());
        assertNull(values[0].getAddress());
        assertNull(values[1].getWord());
        assertNotNull(values[1].getAddress());
    }

    @Test
    public void testDecodeOnlyWord() {
        ArgDecoder decoder = new ArgDecoder();
        String[] args = {"#10101010"};
        Word expected = new Word(new boolean[]{true, false, true, false, true, false, true ,false});

        Result<InstructionValue[]> result = decoder.decode(args);
        assertTrue(result instanceof Result.Ok);

        InstructionValue[] values = ((Result.Ok<InstructionValue[]>) result).getValue();
        assertEquals(1, values.length);
        assertNotNull(values[0].getWord());
        assertArrayEquals(expected.getValue(), values[0].getWord().getValue());
        assertNull(values[0].getAddress());
    }

    @Test
    public void testDecodeOnlyAddress() {
        ArgDecoder decoder = new ArgDecoder();
        String[] args = {"op1"};
        Address expected = new Address("op", 1);

        Result<InstructionValue[]> result = decoder.decode(args);
        assertTrue(result instanceof Result.Ok);

        InstructionValue[] values = ((Result.Ok<InstructionValue[]>) result).getValue();
        assertEquals(1, values.length);
        assertNotNull(values[0].getAddress());
        assertEquals(expected, values[0].getAddress());
        assertNull(values[0].getWord());
    }

    @Test
    public void testDecodeInvalidArg() {
        ArgDecoder decoder = new ArgDecoder();
        String[] args = {"invalid"};

        Result<InstructionValue[]> result = decoder.decode(args);
        assertTrue(result instanceof Result.Error);
    }
}
