package de.dhbw.utils.data;

import de.dhbw.utils.result.Result;

public class Word {
    public final static int WORD_SIZE = 8;

    private boolean[] value;

    public Word() {
        value = new boolean[WORD_SIZE];
    }

    public Word(boolean[] value) {
        this.value = value;
    }

    public Result<?> setValue(boolean[] value) {
        if(value.length == WORD_SIZE) {
            this.value = value;
            return Result.ok();
        } else {
            return Result.error(new Exception("Value not matching word size!"));
        }
    }

    public boolean[] getValue() {
        return value;
    }

    public static Result<Word> fromBinaryString(String input) {
        if (!input.matches("#[01]{" + WORD_SIZE + "}")) {
            return Result.error(new Exception("Invalid format for Word"));
        }

        boolean[] bits = new boolean[WORD_SIZE];
        for (int i = 0; i < WORD_SIZE; i++) {
            bits[i] = input.charAt(i + 1) == '1';
        }

        return Result.ok(new Word(bits));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("#");
        for (boolean bit : value) {
            sb.append(bit ? '1' : '0');
        }
        return sb.toString();
    }

}