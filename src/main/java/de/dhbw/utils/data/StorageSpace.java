package de.dhbw.utils.data;

import de.dhbw.utils.address.Address;

import java.util.ArrayList;
import java.util.List;

public class StorageSpace {
    private final Address address;
    private Word word;

    public StorageSpace(Address address, Word word) {
        this.address = address;
        this.word = word;
    }

    public StorageSpace(Address address) {
        this.address = address;
        this.word = new Word();
    }

    public Address getAddress() {
        return address;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        if(word != null) {
            this.word.setValue(word.getValue());
        }
    }

    public static class StorageSpacesBuilder {

        private List<StorageSpace> storageSpaces;

        public StorageSpacesBuilder() {
            storageSpaces = new ArrayList<>();
        }

        public void add(String prefix, int index) {
            Address address = new Address(prefix, index);
            StorageSpace storageSpace = new StorageSpace(address);
            storageSpaces.add(storageSpace);
        }

        public void addCount(String prefix, int count) {
            addFromTo(prefix, 0, count - 1);
        }

        public void addFromTo(String prefix, int from, int to) {
            int count = to - from + 1;
            StorageSpace[] storageSpaces = new StorageSpace[count];
            for(int i = from; i < to + 1; i++) {
                add(prefix, i);
            }
        }

        public StorageSpace[] build() {
            return storageSpaces.toArray(StorageSpace[]::new);
        }

    }

}
