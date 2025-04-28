package units.control;

import de.dhbw.units.control.ValueParser;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InstructionValueParserTest {

    private final ValueParser parser = new ValueParser();

    @Test
    void testeRichtigenBinärwert() {
        assertTrue(parser.isValue("#00000000"));
        assertTrue(parser.isValue("#11111111"));
    }

    @Test
    void testFalschenBinärwert() {
        assertFalse(parser.isValue("00000000"));   // fehlt #
        assertFalse(parser.isValue("#000"));       // zu kurz
        assertFalse(parser.isValue("#000000002")); // enthält 2
        assertFalse(parser.isValue("#abcdefgh"));  // ungültige Zeichen
    }

    @Test
    void testeValueErkennung() {
        String param = "#10101010";
        Result<InstructionValue> result = parser.getValue(param);
        assertTrue(result instanceof Result.Ok);
        InstructionValue instructionValue = ((Result.Ok<InstructionValue>) result).getValue();
        assertNotNull(instructionValue.getWord());
        assertEquals(Word.WORD_SIZE, instructionValue.getWord().getValue().length);
    }

    @Test
    void testeValueMitBuchstaben() {
        String param = "#10A01010";
        Result<InstructionValue> result = parser.getValue(param);
        assertTrue(result instanceof Result.Error);
    }
}
