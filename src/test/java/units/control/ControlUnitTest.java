package units.control;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.data.Command;
import de.dhbw.units.control.ControlUnit;
import de.dhbw.units.control.StorageManager;
import de.dhbw.units.control.decoder.Decoder;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.instruction.types.GetInstruction;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

public class ControlUnitTest {

    @Test
    public void testProcessAddCommand() {
        // get
        ProcessingUnit mockedGetProcessingUnit = Mockito.mock(ProcessingUnit.class);
        Mockito.when(mockedGetProcessingUnit.canProcess("asdf")).thenReturn(Result.error(new Exception()));

        // set
        ProcessingUnit mockedSetProcessingUnit = Mockito.mock(ProcessingUnit.class);
        Mockito.when(mockedSetProcessingUnit.canProcess("add")).thenReturn(Result.ok());
        Mockito.when(mockedSetProcessingUnit.process(any())).thenReturn(Result.ok());

        // processingUnits
        ProcessingUnit[] mockedProcessingUnits = new ProcessingUnit[]{
                mockedGetProcessingUnit,
                mockedSetProcessingUnit
        };

        // Decoder
        Decoder mockedDecoder = Mockito.mock(Decoder.class);
        Mockito.when(mockedDecoder.decode(any())).thenReturn(Result.ok(new Instruction[] {
                new Instruction("add", new InstructionValue[]{})
        }));

        // storageManager
        StorageManager mockedStorageManager = Mockito.mock(StorageManager.class);
        Mockito.when(mockedStorageManager.canProcess(any())).thenReturn(Result.error(new Exception()));

        // StoredValue
        Word word = new Word();

        // ControlUnit
        ControlUnit controlUnit = new ControlUnit(mockedProcessingUnits, mockedDecoder, mockedStorageManager, word);

        Command addCommand = new Command.CommandBuilder().setKeyword("add").build();
        Result result = controlUnit.process(addCommand);

        assertTrue(result instanceof Result.Ok<?>);
    }

    @Test
    public void testProcessSetCommand() {
        // StoredValue
        boolean[] expected = new boolean[]{true, false, true, false, true, false, true, false};
        Word word = new Word();

        // address
        Address address = new Address("r", 0);

        // asdf
        ProcessingUnit mockedGetProcessingUnit = Mockito.mock(ProcessingUnit.class);
        Mockito.when(mockedGetProcessingUnit.canProcess("asdf")).thenReturn(Result.error(new Exception()));
        Mockito.when(mockedGetProcessingUnit.read(argThat(a -> a.getPrefix().contentEquals(address.getPrefix()) && a.getIndex() == address.getIndex()))).thenReturn(Result.ok(new Word(expected)));

        // add
        ProcessingUnit mockedSetProcessingUnit = Mockito.mock(ProcessingUnit.class);
        Mockito.when(mockedSetProcessingUnit.canProcess("add")).thenReturn(Result.ok());
        Mockito.when(mockedSetProcessingUnit.process(any())).thenReturn(Result.ok());

        // processingUnits
        ProcessingUnit[] mockedProcessingUnits = new ProcessingUnit[]{
                mockedGetProcessingUnit,
                mockedSetProcessingUnit
        };

        // Decoder
        Decoder mockedDecoder = Mockito.mock(Decoder.class);
        Mockito.when(mockedDecoder.decode(any())).thenReturn(Result.ok(new Instruction[] {
                new GetInstruction(new Address("r", 0))
        }));

        // storageManager
        StorageManager storageManager = new StorageManager();

        // ControlUnit
        ControlUnit controlUnit = new ControlUnit(mockedProcessingUnits, mockedDecoder, storageManager, word);

        Command setCommand = new Command.CommandBuilder().setKeyword("get").build();
        Result<?> result = controlUnit.process(setCommand);

        assertTrue(result instanceof Result.Ok<?>);
        assertTrue(((Result.Ok<Word>) result).hasValue());
        assertEquals(((Result.Ok<Word>) result).getValue().getValue(), expected);
    }

    @Test
    public void testProcessInvalidCommand() {
        // get
        ProcessingUnit mockedGetProcessingUnit = Mockito.mock(ProcessingUnit.class);
        Mockito.when(mockedGetProcessingUnit.canProcess("asdf")).thenReturn(Result.error(new Exception()));

        // set
        ProcessingUnit mockedSetProcessingUnit = Mockito.mock(ProcessingUnit.class);
        Mockito.when(mockedSetProcessingUnit.canProcess("add")).thenReturn(Result.error(new Exception()));
        Mockito.when(mockedSetProcessingUnit.process(any())).thenReturn(Result.ok());

        // processingUnits
        ProcessingUnit[] mockedProcessingUnits = new ProcessingUnit[]{
                mockedGetProcessingUnit,
                mockedSetProcessingUnit
        };

        // Decoder
        Decoder mockedDecoder = Mockito.mock(Decoder.class);
        Mockito.when(mockedDecoder.decode(any())).thenReturn(Result.ok(new Instruction[] {
                new Instruction("add", new InstructionValue[]{})
        }));

        // storageManager
        StorageManager mockedStorageManager = Mockito.mock(StorageManager.class);
        Mockito.when(mockedStorageManager.canProcess(any())).thenReturn(Result.error(new Exception()));

        // StoredValue
        Word word = new Word();

        // ControlUnit
        ControlUnit controlUnit = new ControlUnit(mockedProcessingUnits, mockedDecoder, mockedStorageManager, word);

        Command subCommand = new Command.CommandBuilder().setKeyword("sub").build();
        Result result = controlUnit.process(subCommand);

        assertTrue(result instanceof Result.Error<?>);
        assertTrue(((Result.Error<?>) result).getException().getMessage().contentEquals("No matching processing unit!"));
    }

}
