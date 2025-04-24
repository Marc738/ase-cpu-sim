package de.dhbw.utils.data;

import de.dhbw.utils.result.Result;

public class Word {
    public final static int WORD_SIZE = 8;

    private boolean[] value;

    public Word() {
        value = new boolean[WORD_SIZE];
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

}