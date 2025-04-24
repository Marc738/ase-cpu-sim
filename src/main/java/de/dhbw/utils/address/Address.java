package de.dhbw.utils.address;

import de.dhbw.utils.result.Result;

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

}