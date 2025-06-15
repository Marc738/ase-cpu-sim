package de.dhbw.utils.address;

import de.dhbw.utils.result.Result;

import java.util.Objects;

public class Address {

    private final String prefix;
    private final int index;

    public Address(String prefix, int index) {
        this.prefix = prefix;
        this.index = index;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getIndex() {
        return index;
    }

    public static Result<Address> parseRawAddress(String rawAddress) {
        if (!rawAddress.matches("[a-zA-Z]+\\d+")) {
            return Result.error(new IllegalArgumentException("Invalid address format: " + rawAddress));
        }

        try {
            String prefix = rawAddress.replaceAll("\\d+$", "");
            int index = Integer.parseInt(rawAddress.replaceAll("^\\D+", ""));
            return Result.ok(new Address(prefix, index));
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    public static Address defaultAddress() {
        return null;
    }

    public boolean equals(Address address) {
        return address.getPrefix().contentEquals(prefix) && address.getIndex() == index;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Wenn beide Referenzen gleich sind
        if (obj == null || getClass() != obj.getClass()) return false; // Null-Prüfung und gleiche Klasse
        Address address = (Address) obj; // Casten auf die passende Klasse
        return index == address.index && prefix.equals(address.prefix); // Vergleich der Attribute
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, index); // Konsistenter hashCode
    }
}