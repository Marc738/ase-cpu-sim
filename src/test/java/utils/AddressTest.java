package utils;

import de.dhbw.utils.address.Address;
import de.dhbw.utils.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

    @Test
    void testeParseValidAddress() {
        Result<Address> result = Address.parseRawAddress("X12");

        assertTrue(result instanceof Result.Ok);
        Address address = ((Result.Ok<Address>) result).getValue();
        assertEquals("X", address.getPrefix());
        assertEquals(12, address.getIndex());
    }

    @Test
    void testeParseValidMultiCharPrefix() {
        Result<Address> result = Address.parseRawAddress("ABC123");

        assertTrue(result instanceof Result.Ok);
        Address address = ((Result.Ok<Address>) result).getValue();
        assertEquals("ABC", address.getPrefix());
        assertEquals(123, address.getIndex());
    }

    @Test
    void testeParseInvalidAddressNoDigits() {
        Result<Address> result = Address.parseRawAddress("XYZ");

        assertTrue(result instanceof Result.Error);
    }

    @Test
    void testeParseInvalidAddressNoPrefix() {
        Result<Address> result = Address.parseRawAddress("123");

        assertTrue(result instanceof Result.Error);
    }

    @Test
    void testeParseCompletelyInvalidFormat() {
        Result<Address> result = Address.parseRawAddress("!@#");

        assertTrue(result instanceof Result.Error);
    }

    @Test
    void testeDefaultAddressIsNull() {
        assertNull(Address.defaultAddress());
    }
}

