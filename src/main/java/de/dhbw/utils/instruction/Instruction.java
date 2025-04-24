package de.dhbw.utils.instruction;

public class Instruction {
    private final String keyword;
    private final Value[] values;

    public Instruction(String keyword, Value[] values) {
        this.keyword = keyword;
        this.values = values;
    }

    public String getKeyword() {
        return keyword;
    }

    public Value[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(keyword);
        for (Value v : values) {
            sb.append(" [");
            for (boolean b : v.getWord().getValue()) {
                sb.append(b ? "1" : "0");
            }
            sb.append("]");
        }
        return sb.toString();
    }

}