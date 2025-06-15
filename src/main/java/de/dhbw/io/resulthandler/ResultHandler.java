package de.dhbw.io.resulthandler;

import de.dhbw.utils.result.Result;

public interface ResultHandler {

    public String process(Result<?> result);

}
