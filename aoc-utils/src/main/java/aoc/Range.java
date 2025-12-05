package aoc;

public record Range(long min, long max) {
    public boolean containsInclusif(long value) {
        return value >= min && value <= max;
    }

    public boolean containsExclusif(long value) {
        return value > min && value < max;
    }

    public long length() {
        return max - min + 1;
    }
}
