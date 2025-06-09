package units.alu;

import de.dhbw.units.alu.ALU;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.result.Result;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.data.StorageSpace;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ALUTest {

    @Test
    void testCanProcess() {
        ALU alu = new ALU();

        // Test, ob die ALU mit einem gültigen Operator (z.B. "add") umgehen kann
        Result<?> result = alu.canProcess("add");
        assertTrue(result instanceof Result.Ok, "ALU sollte 'add' verarbeiten können.");

        // Test für einen ungültigen Operator
        result = alu.canProcess("invalid");
        assertTrue(result instanceof Result.Error, "ALU sollte 'invalid' nicht verarbeiten können.");
    }

    @Test
    void testProcessAddition() {
        ALU alu = new ALU();
        StorageSpace op1 = new StorageSpace(new Address("op", 1));
        StorageSpace op2 = new StorageSpace(new Address("op", 2));

        // Werte für die Operanden setzen
        Word op1Word = new Word();
        op1Word.setValue(new boolean[]{false, false, false, false, false, false, false, true});
        op1.setWord(op1Word); // 1
        alu.write(ALU.OP1, op1Word);
        Word op2Word = new Word();
        op2Word.setValue(new boolean[]{false, false, false, true, false, false, false, false});
        op2.setWord(op2Word); // 2
        alu.write(ALU.OP2, op2Word);

        // Erstelle eine Instruction
        //InstructionValue[] values = {new InstructionValue(op1.getAddress(), op1.getWord()), new InstructionValue(op2.getAddress(), op2.getWord())};
        Instruction instruction = new Instruction("add", new InstructionValue[]{});

        // Test, ob die ALU die Operation erfolgreich ausführt
        Result<?> result = alu.process(instruction);
        assertTrue(result instanceof Result.Ok, "ALU sollte die 'add' Operation ausführen können.");

        // Test, ob das Ergebnis in res0 korrekt gespeichert wurde (Additionsoperation)
        Result<Word> wordResult = alu.read(new Address("res", 0));
        assertInstanceOf(Result.Ok.class, wordResult);
        boolean[] expected = new boolean[]{false, false, false, true, false, false, false, true}; // 3
        assertArrayEquals(expected, ((Result.Ok<Word>) wordResult).getValue().getValue());
    }

    @Test
    void testProcessSubtraction() {
        ALU alu = new ALU();
        StorageSpace op1 = new StorageSpace(new Address("op", 1));
        StorageSpace op2 = new StorageSpace(new Address("op", 2));

        // Werte für die Operanden setzen
        Word op1Word = new Word();
        op1Word.setValue(new boolean[]{false, false, false, false, false, false, true, true});
        op1.setWord(op1Word); // 1
        alu.write(ALU.OP1, op1Word);
        Word op2Word = new Word();
        op2Word.setValue(new boolean[]{false, false, false, false, false, false, false, true});
        op2.setWord(op2Word); // 2
        alu.write(ALU.OP2, op2Word);

        // Erstelle eine Instruction für Subtraktion
        InstructionValue[] values = {new InstructionValue(op1.getAddress(), op1.getWord()), new InstructionValue(op2.getAddress(), op2.getWord())};
        Instruction instruction = new Instruction("sub", values);

        // Test, ob die ALU die Subtraktionsoperation korrekt ausführt
        Result<?> result = alu.process(instruction);
        assertTrue(result instanceof Result.Ok, "ALU sollte die 'subtract' Operation ausführen können.");

        // Test, ob das Ergebnis korrekt berechnet wurde (Subtraktion: 1 - 1 = 0)
        Result<Word> wordResult = alu.read(new Address("res", 0));
        assertInstanceOf(Result.Ok.class, wordResult);
        assertInstanceOf(Word.class, ((Result.Ok<Word>) wordResult).getValue());
        boolean[] expected = new boolean[]{false, false, false, false, false, false, true, false};
        assertArrayEquals(expected, ((Result.Ok<Word>) wordResult).getValue().getValue());
    }

    @Test
    void testReadAndWrite() {
        ALU alu = new ALU();

        // Test, ob die ALU das richtige Wort zurückgibt
        Address address = new Address("op", 1);
        Result<Word> wordResult = alu.read(address);
        assertTrue(wordResult instanceof Result.Ok, "Die ALU sollte das Wort aus der richtigen Speicheradresse zurückgeben.");

        // Test, ob die ALU das Wort korrekt schreibt
        Word newWord = new Word();
        newWord.setValue(new boolean[]{true, true, true, true, true, true, true, true});
        Result<?> writeResult = alu.write(address, newWord);
        assertTrue(writeResult instanceof Result.Ok, "Die ALU sollte das Wort erfolgreich schreiben.");

        // Überprüfen, ob der Wert nach dem Schreiben korrekt ist
        wordResult = alu.read(address);
        assertInstanceOf(Result.Ok.class, wordResult);
        assertArrayEquals(newWord.getValue(), ((Result.Ok<Word>) wordResult).getValue().getValue());
    }

    @Test
    void testInvalidAddressForWrite() {
        ALU alu = new ALU();

        // Versuchen, ein Wort an einer nicht existierenden Adresse zu schreiben
        Address invalidAddress = new Address("invalid", 99);
        Word newWord = new Word();
        newWord.setValue(new boolean[]{false, true, false, true, false, true, false, true});
        Result<?> result = alu.write(invalidAddress, newWord);

        assertTrue(result instanceof Result.Error, "Schreiben an einer ungültigen Adresse sollte fehlschlagen.");
    }

    @Test
    void testFindStorageSpaceByAddress() {
        ALU alu = new ALU();

        // Test, ob die richtige Speicheradresse zurückgegeben wird
        Address address = new Address("op", 1);
        Result<Word> storageSpaceResult = alu.read(address);
        assertTrue(storageSpaceResult instanceof Result.Ok, "Die ALU sollte den Speicherplatz mit der Adresse 'op1' finden.");

        // Test für eine nicht existierende Adresse
        Address invalidAddress = new Address("op", 99);
        storageSpaceResult = alu.read(invalidAddress);
        assertTrue(storageSpaceResult instanceof Result.Error, "Die ALU sollte einen Fehler zurückgeben, wenn die Adresse nicht gefunden wird.");
    }

    @Test
    void testProcessWithEmptyInstruction() {
        ALU alu = new ALU();
        Instruction instruction = new Instruction("", new InstructionValue[]{});

        // Test, ob ein leeres Instruction-Objekt einen Fehler erzeugt
        Result<?> result = alu.process(instruction);
        assertTrue(result instanceof Result.Error, "Die ALU sollte einen Fehler zurückgeben, wenn eine leere Instruction verarbeitet wird.");
    }
}
