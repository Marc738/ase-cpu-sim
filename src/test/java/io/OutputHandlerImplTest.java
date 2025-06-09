package io;

import static org.mockito.Mockito.*;

import de.dhbw.io.output.OutputHandlerImpl;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

public class OutputHandlerImplTest {

    private OutputHandlerImpl outputHandler;
    private PrintStream mockStream;

    @BeforeEach
    public void setup() {
        outputHandler = new OutputHandlerImpl();
        mockStream = mock(PrintStream.class);

        try {
            var field = OutputHandlerImpl.class.getDeclaredField("printStream");
            field.setAccessible(true);
            field.set(outputHandler, mockStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPrintOkResult() {
        Result<?> result = new Result.Ok<>(null);
        outputHandler.print(result);
        verify(mockStream).println(OutputHandlerImpl.OK_COLOR +
                "Command wurde ohne Fehler ausgeführt!" +
                OutputHandlerImpl.REST_COLOR);
    }

    @Test
    public void testPrintErrorResult() {
        String message = "Fehler!";
        Result<?> result = new Result.Error<>(new Exception(message));
        outputHandler.print(result);
        verify(mockStream).println(OutputHandlerImpl.ERROR_COLOR +
                message +
                OutputHandlerImpl.REST_COLOR);
    }

    @Test
    public void testPrintUnknownResult() {
        // Simuliere unbekannten Result-Typ
        Result<?> result = new Result<>() {};
        outputHandler.print(result);
        verify(mockStream).println(OutputHandlerImpl.FATAL_ERROR_COLOR +
                "FATAL ERROR: Command hat unerwarteten Fehler ausgelöst" +
                OutputHandlerImpl.REST_COLOR);
    }
}
