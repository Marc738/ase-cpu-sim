package de.dhbw.utils.instruction;

public class Instruction {
    private final String keyword;
    private final InstructionValue[] instructionValues;

    public Instruction(String keyword, InstructionValue[] instructionValues) {
        this.keyword = keyword;
        this.instructionValues = instructionValues;
    }

    public String getKeyword() {
        return keyword;
    }

    public InstructionValue[] getValues() {
        return instructionValues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(keyword);
        for (InstructionValue v : instructionValues) {
            sb.append(" [");
            for (boolean b : v.getWord().getValue()) {
                sb.append(b ? "1" : "0");
            }
            sb.append("]");
        }
        return sb.toString();
    }

}