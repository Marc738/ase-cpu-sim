package units.control;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.units.control.AddressResolver;
import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;
import de.dhbw.utils.instruction.Instruction;
import de.dhbw.utils.instruction.InstructionValue;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddressResolverTest {

    static class TestStorageUnit implements ProcessingUnit {
        private final Address knownAddress;
        private final Word wordToReturn;

        // StorageUnit hat nur eine Adresse und ein zugehöriges Word
        TestStorageUnit(Address address, Word word) {
            this.knownAddress = address;
            this.wordToReturn = word;
        }

        @Override
        public Result<?> canProcess(String keyword) {
            return Result.error(new UnsupportedOperationException("Not implemented"));
        }

        @Override
        public Result<?> process(Instruction instruction) {
            return Result.error(new UnsupportedOperationException("Not implemented"));
        }

        @Override
        public Result<Word> read(Address address) {
            if (address.getPrefix().equals(knownAddress.getPrefix()) &&
                    address.getIndex() == knownAddress.getIndex()) {
                return Result.ok(wordToReturn);
            }
            return Result.error(new Exception("Unknown address"));
        }

        @Override
        public Result<?> write(Address address, Word word) {
            return Result.error(new UnsupportedOperationException("Not implemented"));
        }
    }

    @Test
    void testeGetAddressAndValueErfolgreich() {
        AddressResolver resolver = new AddressResolver();

        Address address = new Address("r", 5);
        Word word = new Word();
        word.setValue(new boolean[Word.WORD_SIZE]);

        ProcessingUnit unit = new TestStorageUnit(address, word);
        Result<InstructionValue> result = resolver.getAddressAndValue(new ProcessingUnit[]{unit}, "r5");

        assertTrue(result instanceof Result.Ok);
        InstructionValue instructionValue = ((Result.Ok<InstructionValue>) result).getValue();
        assertEquals("r", instructionValue.getAddress().getPrefix());
        assertEquals(5, instructionValue.getAddress().getIndex());
        assertArrayEquals(word.getValue(), instructionValue.getWord().getValue());
    }

    @Test
    void testeGetAddressAndValueMitInvaliderAddress() {
        AddressResolver resolver = new AddressResolver();

        Address address = new Address("r", 5);
        Word word = new Word();
        word.setValue(new boolean[Word.WORD_SIZE]);

        ProcessingUnit unit = new TestStorageUnit(address, word);

        Result<InstructionValue> result = resolver.getAddressAndValue(new ProcessingUnit[]{unit}, "!!invalid");

        assertTrue(result instanceof Result.Error);
    }

    @Test
    void testeGetAddressAndValueMitUngültigerAddresse() {
        AddressResolver resolver = new AddressResolver();

        Address known = new Address("r", 5);
        Word dummy = new Word();
        dummy.setValue(new boolean[Word.WORD_SIZE]);

        ProcessingUnit unit = new TestStorageUnit(known, dummy);
        Result<InstructionValue> result = resolver.getAddressAndValue(new ProcessingUnit[]{unit}, "r2");

        assertTrue(result instanceof Result.Error);
    }
}
