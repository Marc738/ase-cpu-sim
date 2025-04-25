package de.dhbw.units.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {

    private final String keyword;
    private final List<String> params;

    private Command(CommandBuilder commandBuilder) {
        this.keyword = commandBuilder.keyword;
        this.params = commandBuilder.params;
    }

    public String getKeyword() {
        return keyword;
    }

    public List<String> getParams() {
        return params;
    }

    public static Command fromString(String input) {
        String[] parts = input.trim().split("\\s+");
        String keyword = parts[0];
        List<String> params = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length));
        return new CommandBuilder()
                .setKeyword(keyword)
                .setParams(params)
                .build();
    }

    public static class CommandBuilder {
        String keyword;
        List<String> params = new ArrayList<>();

        public CommandBuilder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public CommandBuilder setParams(List<String> params) {
            this.params = params;
            return this;
        }

        public Command build() {
            return new Command(this);
        }
    }

}
