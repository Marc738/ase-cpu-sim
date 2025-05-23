package de.dhbw.units.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {

    private final String keyword;
    private final List<String> args;

    private Command(CommandBuilder commandBuilder) {
        this.keyword = commandBuilder.keyword;
        this.args = commandBuilder.args;
    }

    public String getKeyword() {
        return keyword;
    }

    public List<String> getArgs() {
        return args;
    }

    public static Command fromString(String input) {
        String[] parts = input.trim().split("\\s+");
        String keyword = parts[0];
        List<String> params = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length));
        return new CommandBuilder()
                .setKeyword(keyword)
                .setArgs(params)
                .build();
    }

    public static class CommandBuilder {
        String keyword;
        List<String> args = new ArrayList<>();

        public CommandBuilder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public CommandBuilder setArgs(List<String> args) {
            this.args = args;
            return this;
        }

        public CommandBuilder addArgs(String arg) {
            args.add(arg);
            return this;
        }

        public Command build() {
            return new Command(this);
        }
    }

}
