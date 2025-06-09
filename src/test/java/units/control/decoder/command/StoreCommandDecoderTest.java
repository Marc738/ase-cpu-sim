package units.control.decoder.command;

import de.dhbw.units.control.decoder.command.StoreCommandDecoder;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.instruction.types.StoreInstruction;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreCommandDecoderTest {

    @Test
    void testValidDecode() {
        boolean[] bits = new boolean[] {true, false, true, false, true, false, true, false};
        Word word = new Word(bits);
        InstructionValue[] args = new InstructionValue[] {
                new InstructionValue(null, word)
        };

        StoreCommandDecoder decoder = new StoreCommandDecoder();
        Result<Instruction[]> result = decoder.decode("store", args);

        assertTrue(result instanceof Result.Ok<Instruction[]>);
        Instruction[] instructions = ((Result.Ok<Instruction[]>) result).getValue();
        assertEquals(1, instructions.length);
        assertTrue(instructions[0] instanceof StoreInstruction);
        assertEquals(word, ((StoreInstruction) instructions[0]).getValues()[0].getWord());
    }

    @Test
    void testInvalidKeyword() {
        boolean[] bits = new boolean[] {false, false, false, false, true, true, true, true};
        Word word = new Word(bits);
        InstructionValue[] args = new InstructionValue[] {
                new InstructionValue(null, word)
        };

        StoreCommandDecoder decoder = new StoreCommandDecoder();
        Result<Instruction[]> result = decoder.decode("set", args);

        assertTrue(result instanceof Result.Error<Instruction[]>);
    }

    @Test
    void testNullWord() {
        InstructionValue[] args = new InstructionValue[] {
                new InstructionValue(null, null)
        };

        StoreCommandDecoder decoder = new StoreCommandDecoder();
        Result<Instruction[]> result = decoder.decode("store", args);

        assertTrue(result instanceof Result.Error<Instruction[]>);
    }

    @Test
    void testCanDecodeCommand() {
        boolean[] bits = new boolean[] {true, true, false, false, true, false, false, true};
        Word word = new Word(bits);
        StoreCommandDecoder decoder = new StoreCommandDecoder();

        InstructionValue[] valid = new InstructionValue[] {
                new InstructionValue(null, word)
        };
        assertTrue(decoder.canDecodeCommand("store", valid));

        InstructionValue[] wrongKeyword = new InstructionValue[] {
                new InstructionValue(null, word)
        };
        assertFalse(decoder.canDecodeCommand("set", wrongKeyword));

        InstructionValue[] emptyArgs = new InstructionValue[0];
        assertFalse(decoder.canDecodeCommand("store", emptyArgs));

        InstructionValue[] nullWord = new InstructionValue[] {
                new InstructionValue(null, null)
        };
        assertFalse(decoder.canDecodeCommand("store", nullWord));
    }
}
