package aoc.day08;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
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
}
