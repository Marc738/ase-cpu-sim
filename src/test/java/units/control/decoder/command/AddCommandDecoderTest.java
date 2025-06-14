package units.control.decoder.command;

import de.dhbw.units.control.decoder.command.AddCommandDecoder;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.*;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddCommandDecoderTest {

    @Test
    void decode_shouldReturnInstructions_whenValidArgs() {
        AddCommandDecoder decoder = new AddCommandDecoder();

        InstructionValue[] values = new InstructionValue[] {
                new InstructionValue(new Address("r", 1), null),
                new InstructionValue(null, new Word(new boolean[]{ true, false, true }))
        };

        Result<Instruction[]> result = decoder.decode("add", values);

        assertTrue(result instanceof Result.Ok);
        Instruction[] instructions = ((Result.Ok<Instruction[]>) result).getValue();

        assertEquals(5, instructions.length);
        assertEquals("add", instructions[4].getKeyword());
    }

    @Test
    void decode_shouldReturnError_whenInvalidKeyword() {
        AddCommandDecoder decoder = new AddCommandDecoder();

        InstructionValue[] values = new InstructionValue[] {
                new InstructionValue(new Address("r", 1), null),
                new InstructionValue(null, new Word(new boolean[]{ true }))
        };

        Result<Instruction[]> result = decoder.decode("sub", values);

        assertTrue(result instanceof Result.Error);
        assertEquals("Can not decode command!", ((Result.Error<Instruction[]>) result).getException().getMessage());
    }

    @Test
    void decode_shouldReturnError_whenInvalidArgs() {
        AddCommandDecoder decoder = new AddCommandDecoder();

        InstructionValue[] values = new InstructionValue[] {
                new InstructionValue(null, null),
                new InstructionValue(null, null)
        };

        Result<Instruction[]> result = decoder.decode("add", values);

        assertTrue(result instanceof Result.Error);
        assertEquals("Invalid decoding of arg!", ((Result.Error<Instruction[]>) result).getException().getMessage());
    }

    @Test
    void canDecodeCommand_shouldOnlyAcceptAddAndTwoArgs() {
        AddCommandDecoder decoder = new AddCommandDecoder();

        InstructionValue[] valid = new InstructionValue[2];
        assertTrue(decoder.canDecodeCommand("add", valid));

        InstructionValue[] invalidCount = new InstructionValue[1];
        assertFalse(decoder.canDecodeCommand("add", invalidCount));

        InstructionValue[] invalidKeyword = new InstructionValue[2];
        assertFalse(decoder.canDecodeCommand("sub", invalidKeyword));
    }
}
