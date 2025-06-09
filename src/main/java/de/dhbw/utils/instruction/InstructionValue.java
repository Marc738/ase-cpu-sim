package de.dhbw.utils.instruction;

import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.Word;

public class InstructionValue {
    private Address address;
    private Word word;

    public InstructionValue(Address address, Word word) {
        this.address = address;
        this.word = word;
    }

    public Address getAddress() {
        return address;
    }

    public Word getWord() {
        return word;
    }
}