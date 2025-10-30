package aoc.day08;

import com.google.common.collect.Lists;

import java.util.HashSet;
import java.util.Set;

public class DisplaySignal {
    public static final Set<Integer> UNIQUE_LENGTHS = Set.of(2, 3, 4, 7);

    private final Set<Character> value;

    public DisplaySignal(String value) {
        this.value = new HashSet<>(Lists.charactersOf(value.trim()));
    }

    public int size() {
        return value.size();
    }

    public boolean contains(DisplaySignal other) {
        return this.value.containsAll(other.getValue());
    }

    public boolean hasUniqueLength() {
        return UNIQUE_LENGTHS.contains(size());
    }

    public Set<Character> getValue() {
        return this.value;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DisplaySignal)) return false;
        final DisplaySignal other = (DisplaySignal) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DisplaySignal;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $value = this.getValue();
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        return result;
    }

    public String toString() {
        return "DisplaySignal(value=" + this.getValue() + ")";
    }
}
