package io;

import de.dhbw.io.input.InputHandlerImpl;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class InputHandlerImplTest {

    @Test
    void testReadReturnsCorrectInput() {
        String input = "hello world\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in); // System.in umleiten

        InputHandlerImpl handler = new InputHandlerImpl();
        String result = handler.read();

        assertEquals("hello world", result);
    }
}
