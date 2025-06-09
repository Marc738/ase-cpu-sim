package units.control.decoder.command;

import de.dhbw.units.control.decoder.command.GetCommandDecoder;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.instruction.types.GetInstruction;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetCommandDecoderTest {

    @Test
    void testValidDecode() {
        Address address = new Address("r", 5); // Beispieladresse
        InstructionValue[] values = new InstructionValue[] {
                new InstructionValue(address, null)
        };

        GetCommandDecoder decoder = new GetCommandDecoder();
        Result<Instruction[]> result = decoder.decode("get", values);

        assertTrue(result instanceof Result.Ok<Instruction[]>);
        Instruction[] instructions = ((Result.Ok<Instruction[]>) result).getValue();
        assertEquals(1, instructions.length);
        assertTrue(instructions[0] instanceof GetInstruction);
        assertEquals(address, ((GetInstruction) instructions[0]).getValues()[0].getAddress());
    }

    @Test
    void testInvalidKeyword() {
        Address address = new Address("r", 5);
        InstructionValue[] values = new InstructionValue[] {
                new InstructionValue(address, null)
        };

        GetCommandDecoder decoder = new GetCommandDecoder();
        Result<Instruction[]> result = decoder.decode("set", values);

        assertTrue(result instanceof Result.Error<Instruction[]>);
    }

    @Test
    void testMissingAddress() {
        InstructionValue[] values = new InstructionValue[] {
                new InstructionValue(null, null)
        };

        GetCommandDecoder decoder = new GetCommandDecoder();
        Result<Instruction[]> result = decoder.decode("get", values);

        assertTrue(result instanceof Result.Error<Instruction[]>);
    }
}

