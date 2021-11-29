package aoc.day14;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@Value
@AllArgsConstructor
public class Memory {
    public long index;
    public long value;
}
