package aoc.day08;

public class Instruction {
    String operation;
    long number;

    public Instruction(String operation, long number) {
        this.operation = operation;
        this.number = number;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Instruction)) return false;
        final Instruction other = (Instruction) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$operation = this.getOperation();
        final Object other$operation = other.getOperation();
        if (this$operation == null ? other$operation != null : !this$operation.equals(other$operation)) return false;
        if (this.getNumber() != other.getNumber()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Instruction;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $operation = this.getOperation();
        result = result * PRIME + ($operation == null ? 43 : $operation.hashCode());
        final long $number = this.getNumber();
        result = result * PRIME + (int) ($number >>> 32 ^ $number);
        return result;
    }

    public String toString() {
        return "Instruction(operation=" + this.getOperation() + ", number=" + this.getNumber() + ")";
    }

    public String getOperation() {
        return this.operation;
    }

    public long getNumber() {
        return this.number;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
