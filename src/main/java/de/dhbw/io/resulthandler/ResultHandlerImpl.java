package de.dhbw.io.resulthandler;

import de.dhbw.utils.data.Word;
import de.dhbw.utils.result.Result;

public class ResultHandlerImpl implements ResultHandler {

    public static final String REST_COLOR = "\u001B[0m";
    public static final String OK_COLOR = "\u001B[32m";
    public static final String ERROR_COLOR = "\u001B[33m";
    public static final String FATAL_ERROR_COLOR = "\u001B[31m";

    @Override
    public String process(Result<?> result) {
        if(result instanceof Result.Ok<?> ok) {
            String pre = print("Command wurde ohne Fehler ausgeführt!", OK_COLOR);
            if(ok.hasValue() && ok.getValue() instanceof Word word) {
                return pre + "\n" + print("Neuer Wert in StoredValue: " + word.toString(), OK_COLOR);
            }
            return pre;
        } else if(result instanceof Result.Error<?> error) {
            return print(error.getException().getMessage(), ERROR_COLOR);
        } else {
            return print("FATAL ERROR: Command hat unerwarteten Fehler ausgelöst", FATAL_ERROR_COLOR);
        }
    }

    private String print(String text, String colorCode) {
        return colorCode + text + REST_COLOR;
    }
}
