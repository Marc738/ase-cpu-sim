package units.control;

import de.dhbw.units.control.StorageManager;
import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StorageManagerTest {

    @Test
    public void testeCanProcessSetErfolg() {
        Address a1 = new Address("r", 1);
        Word w = new Word();
        InstructionValue v1 = new InstructionValue(a1, null);
        Instruction instr = new Instruction("set", new InstructionValue[]{v1});

        StorageManager sm = new StorageManager();
        Result<?> res = sm.canProcess(instr);

        assertTrue(res instanceof Result.Ok<?>);
    }

    @Test
    public void testeCanProcessGetErfolg() {
        Address a1 = new Address("r", 1);
        InstructionValue v1 = new InstructionValue(a1, null);
        Instruction instr = new Instruction("get", new InstructionValue[]{v1});

        StorageManager sm = new StorageManager();
        Result<?> res = sm.canProcess(instr);

        assertTrue(res instanceof Result.Ok<?>);
    }

    @Test
    public void testeCanProcessStoreErfolg() {
        InstructionValue v1 = new InstructionValue(null, new Word());
        Instruction instr = new Instruction("store", new InstructionValue[]{v1});

        StorageManager sm = new StorageManager();
        Result<?> res = sm.canProcess(instr);

        assertTrue(res instanceof Result.Ok<?>);
    }

    @Test
    public void testeCanProcessStoreOhneInstructionValue() {
        InstructionValue v1 = new InstructionValue(null, new Word());
        Instruction instr = new Instruction("store", new InstructionValue[]{});

        StorageManager sm = new StorageManager();
        Result<?> res = sm.canProcess(instr);

        assertTrue(res instanceof Result.Error<?>);
    }

    @Test
    public void testeProcessSetErfolgreich() {
        Address a1 = new Address("r", 1);
        Word w = new Word();
        InstructionValue v1 = new InstructionValue(a1, null);
        Instruction instr = new Instruction("set", new InstructionValue[]{v1});

        ProcessingUnit mockUnit = mock(ProcessingUnit.class);
        when(mockUnit.read(a1)).thenReturn(Result.ok(new Word()));
        when(mockUnit.write(a1, w)).thenReturn(Result.ok());

        StorageManager sm = new StorageManager();
        Result<?> res = sm.process(new ProcessingUnit[]{mockUnit}, w, instr);

        assertTrue(res instanceof Result.Ok<?>);
    }

    @Test
    public void testeProcessGetErfolgreich() {
        Address a1 = new Address("r", 1);
        Word w = new Word();
        InstructionValue v1 = new InstructionValue(a1, null);
        Instruction instr = new Instruction("get", new InstructionValue[]{v1});

        ProcessingUnit mockUnit = mock(ProcessingUnit.class);
        when(mockUnit.read(a1)).thenReturn(Result.ok(w)); // wird beim Finden und beim Lesen genutzt

        StorageManager sm = new StorageManager();
        Result<?> res = sm.process(new ProcessingUnit[]{mockUnit}, new Word(), instr);

        assertTrue(res instanceof Result.Ok<?>);
    }

    @Test
    public void testeProcessStoreErfolgreich() {
        boolean[] expected = new boolean[]{true, false, true, false, true, false, true, false};
        Word w = new Word();
        InstructionValue v1 = new InstructionValue(null, new Word(expected));
        Instruction instr = new Instruction("store", new InstructionValue[]{v1});

        StorageManager sm = new StorageManager();
        Result<?> res = sm.process(new ProcessingUnit[]{}, w, instr);

        assertTrue(res instanceof Result.Ok<?>);
        assertArrayEquals(expected, w.getValue());
    }

    @Test
    public void testeProcessMitFalschemKeyword() {
        Address a1 = new Address("r", 1);
        InstructionValue v1 = new InstructionValue(a1, null);
        Instruction instr = new Instruction("unknown", new InstructionValue[]{v1});

        StorageManager sm = new StorageManager();
        Result<?> res = sm.process(new ProcessingUnit[]{}, new Word(), instr);

        assertTrue(res instanceof Result.Error<?>);
    }
}