package de.dhbw.units.alu.subunit;

import de.dhbw.utils.data.Word;
import de.dhbw.utils.result.Result;

public interface SubUnit {
    Result<?> canProcess(String keyword);
    Result<OperatorResult> process(String keyword, Word op1, Word op2);
}
