package units.control;

import de.dhbw.units.control.StorageManager;
import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.Value;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StorageManagerTest {

    @Test
    public void testeCanProcessErfolg() {
        Address a1 = new Address("r", 1);
        Address a2 = new Address("r", 2);
        Word w = new Word();

        Value v1 = new Value(a1, w);
        Value v2 = new Value(a2, w);
        Instruction instr = new Instruction("mv", new Value[]{v1, v2});

        StorageManager sm = new StorageManager();
        Result<?> res = sm.canProcess(instr);

        assertTrue(res instanceof Result.Ok<?>);
    }

    @Test
    public void testeCanProcessFalschesKeyword() {
        Instruction instr = new Instruction("add", new Value[2]);
        StorageManager sm = new StorageManager();
        Result<?> res = sm.canProcess(instr);
        assertTrue(res instanceof Result.Error<?>);
    }

    @Test
    public void testeProcessErfolgreich() {
        Address a1 = new Address("r", 1);
        Address a2 = new Address("r", 2);
        Word w = new Word();

        Value target = new Value(a1, w);
        Value source = new Value(a2, w);
        Instruction instr = new Instruction("mv", new Value[]{target, source});

        ProcessingUnit mockUnit = mock(ProcessingUnit.class);
        when(mockUnit.read(a1)).thenReturn(Result.ok(w));
        when(mockUnit.write(a1, w)).thenReturn(Result.ok());

        StorageManager sm = new StorageManager();
        Result<?> res = sm.process(new ProcessingUnit[]{mockUnit}, instr);

        assertTrue(res instanceof Result.Ok<?>);
    }

    @Test
    public void testeProcessOhnePassendeUnit() {
        Address a1 = new Address("r", 1);
        Address a2 = new Address("r", 2);
        Word w = new Word();

        Value target = new Value(a1, w);
        Value source = new Value(a2, w);
        Instruction instr = new Instruction("mv", new Value[]{target, source});

        ProcessingUnit mockUnit = mock(ProcessingUnit.class);
        when(mockUnit.read(a1)).thenReturn(Result.error(new Exception()));

        StorageManager sm = new StorageManager();
        Result<?> res = sm.process(new ProcessingUnit[]{mockUnit}, instr);

        assertTrue(res instanceof Result.Error<?>);
    }
}

