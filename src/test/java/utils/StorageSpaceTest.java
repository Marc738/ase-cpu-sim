package utils;

import de.dhbw.utils.address.Address;
import de.dhbw.utils.data.StorageSpace;
import de.dhbw.utils.data.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageSpaceTest {

    @Test
    void testConstructorWithWord() {
        Address address = new Address("A", 1);
        Word word = new Word();
        StorageSpace space = new StorageSpace(address, word);

        assertEquals(address, space.getAddress());
        assertEquals(word, space.getWord());
    }

    @Test
    void testConstructorWithoutWord() {
        Address address = new Address("B", 2);
        StorageSpace space = new StorageSpace(address);

        assertEquals(address, space.getAddress());
        assertNotNull(space.getWord());
    }

    @Test
    void testSetAndGetWord() {
        Address address = new Address("C", 3);
        StorageSpace space = new StorageSpace(address);
        Word newWord = new Word();

        boolean[] value = new boolean[]{true, false, true, false, true, false, true, false};
        newWord.setValue(value);

        space.setWord(newWord);
        assertArrayEquals(value, space.getWord().getValue());
    }

    @Test
    void testStorageSpacesBuilderAdd() {
        StorageSpace.StorageSpacesBuilder builder = new StorageSpace.StorageSpacesBuilder();
        builder.add("D", 4);

        StorageSpace[] spaces = builder.build();
        assertEquals(1, spaces.length);
        assertEquals(new Address("D", 4), spaces[0].getAddress());
    }

    @Test
    void testStorageSpacesBuilderAddCount() {
        StorageSpace.StorageSpacesBuilder builder = new StorageSpace.StorageSpacesBuilder();
        builder.addCount("E", 3);

        StorageSpace[] spaces = builder.build();
        assertEquals(3, spaces.length);
        assertEquals(new Address("E", 0), spaces[0].getAddress());
        assertEquals(new Address("E", 1), spaces[1].getAddress());
        assertEquals(new Address("E", 2), spaces[2].getAddress());
    }

    @Test
    void testStorageSpacesBuilderAddFromTo() {
        StorageSpace.StorageSpacesBuilder builder = new StorageSpace.StorageSpacesBuilder();
        builder.addFromTo("F", 5, 7);

        StorageSpace[] spaces = builder.build();
        assertEquals(3, spaces.length);
        assertEquals(new Address("F", 5), spaces[0].getAddress());
        assertEquals(new Address("F", 6), spaces[1].getAddress());
        assertEquals(new Address("F", 7), spaces[2].getAddress());
    }
}
