package de.dhbw.units.alu.subunit;

import de.dhbw.utils.data.Word;
import de.dhbw.utils.result.Result;

public abstract class Operator {

    private final String keyword;

    public Operator(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public Result<?> canProcess(String requestKeyword) {
        if(requestKeyword.contentEquals(keyword)) {
            return Result.ok();
        } else {
            return Result.error(new Exception("Keyword not matching!"));
        }
    }

    public abstract OperatorResult process(Word op1, Word op2);

}
