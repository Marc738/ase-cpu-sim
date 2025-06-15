package io;

import de.dhbw.io.inputconverter.InputConverterImpl;
import de.dhbw.utils.data.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputConverterTest {

    @Test
    void testToCommand() {
        InputConverterImpl converter = new InputConverterImpl();
        String input = "load R1 #10101010";
        Command cmd = converter.toCommand(input);

        assertEquals("load", cmd.getKeyword());
        assertEquals(2, cmd.getArgs().size());
        assertEquals("R1", cmd.getArgs().get(0));
        assertEquals("#10101010", cmd.getArgs().get(1));
    }

}
