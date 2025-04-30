package units.control;

import de.dhbw.units.control.Command;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void testeFromString() {
        String input = "add #01010101 r2";
        Command command = Command.fromString(input);

        assertEquals("add", command.getKeyword());
        assertEquals(List.of("#01010101", "r2"), command.getArgs());
    }

    @Test
    void testeNurMitKeyword() {
        String input = "exit";
        Command command = Command.fromString(input);

        assertEquals("exit", command.getKeyword());
        assertTrue(command.getArgs().isEmpty());
    }

    @Test
    void testeCommandErstellungMitCommandBuilder() {
        Command command = new Command.CommandBuilder()
                .setKeyword("add")
                .setArgs(List.of("#01010101"))
                .build();

        assertEquals("add", command.getKeyword());
        assertEquals(List.of("#01010101"), command.getArgs());
    }
}
