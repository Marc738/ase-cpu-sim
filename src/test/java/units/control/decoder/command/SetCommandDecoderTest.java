package units.control.decoder.command;

import de.dhbw.units.control.decoder.command.SetCommandDecoder;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.instruction.types.SetInstruction;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetCommandDecoderTest {

    @Test
    void testValidDecode() {
        Address address = new Address("r", 1);
        InstructionValue[] args = new InstructionValue[] {
                new InstructionValue(address, null)
        };

        SetCommandDecoder decoder = new SetCommandDecoder();
        Result<Instruction[]> result = decoder.decode("set", args);

        assertTrue(result instanceof Result.Ok<Instruction[]>);
        Instruction[] instructions = ((Result.Ok<Instruction[]>) result).getValue();
        assertEquals(1, instructions.length);
        assertTrue(instructions[0] instanceof SetInstruction);
        assertEquals(address, ((SetInstruction) instructions[0]).getValues()[0].getAddress());
    }

    @Test
    void testInvalidKeyword() {
        Address address = new Address("r", 2);
        InstructionValue[] args = new InstructionValue[] {
                new InstructionValue(address, null)
        };

        SetCommandDecoder decoder = new SetCommandDecoder();
        Result<Instruction[]> result = decoder.decode("store", args);

        assertTrue(result instanceof Result.Error<Instruction[]>);
    }

    @Test
    void testNullAddress() {
        InstructionValue[] args = new InstructionValue[] {
                new InstructionValue(null, null)
        };

        SetCommandDecoder decoder = new SetCommandDecoder();
        Result<Instruction[]> result = decoder.decode("set", args);

        assertTrue(result instanceof Result.Error<Instruction[]>);
    }

    @Test
    void testCanDecodeCommand() {
        SetCommandDecoder decoder = new SetCommandDecoder();

        InstructionValue[] valid = new InstructionValue[] {
                new InstructionValue(new Address("r", 3), null)
        };
        assertTrue(decoder.canDecodeCommand("set", valid));

        InstructionValue[] wrongKeyword = new InstructionValue[] {
                new InstructionValue(new Address("r", 3), null)
        };
        assertFalse(decoder.canDecodeCommand("get", wrongKeyword));

        InstructionValue[] emptyArgs = new InstructionValue[0];
        assertFalse(decoder.canDecodeCommand("set", emptyArgs));

        InstructionValue[] nullAddr = new InstructionValue[] {
                new InstructionValue(null, null)
        };
        assertFalse(decoder.canDecodeCommand("set", nullAddr));
    }
}

