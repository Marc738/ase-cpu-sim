package units.control.decoder;

import de.dhbw.units.control.Command;
import de.dhbw.units.control.decoder.ArgDecoder;
import de.dhbw.units.control.decoder.command.CommandDecoder;
import de.dhbw.units.control.decoder.Decoder;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DecoderTest {

    @Test
    void decode_shouldReturnOk_whenMatchingDecoderExists() {
        // Command vorbereiten
        Command command = Command.fromString("MOVE #01010101");

        // Dummy-Instruction und Decoder
        Instruction dummyInstruction = new Instruction("MOVE", new InstructionValue[0]);
        CommandDecoder matchingDecoder = new CommandDecoder("MOVE") {
            @Override
            public Result<Instruction[]> decode(String keyword, InstructionValue[] instructionValues) {
                return Result.ok(new Instruction[]{dummyInstruction});
            }
        };

        Decoder decoder = new Decoder(new ArgDecoder(), new CommandDecoder[]{matchingDecoder});

        Result<Instruction[]> result = decoder.decode(command);

        assertTrue(result instanceof Result.Ok);
        assertEquals("MOVE", ((Result.Ok<Instruction[]>) result).getValue()[0].getKeyword());
    }

    @Test
    void decode_shouldReturnError_whenNoDecoderMatches() {
        Command command = Command.fromString("JUMP 42");

        CommandDecoder nonMatchingDecoder = new CommandDecoder("MOVE") {
            @Override
            public Result<Instruction[]> decode(String keyword, InstructionValue[] instructionValues) {
                return Result.error(new Exception("Not supported"));
            }
        };

        Decoder decoder = new Decoder(new ArgDecoder(), new CommandDecoder[]{nonMatchingDecoder});

        Result<Instruction[]> result = decoder.decode(command);

        assertTrue(result instanceof Result.Error);
        assertEquals("Args could not be decoded!", ((Result.Error<Instruction[]>) result).getException().getMessage());
    }

}

