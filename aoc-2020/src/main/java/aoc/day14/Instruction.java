package aoc.day14;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Data
@Value
@AllArgsConstructor
public class Instruction {

    public String mem;
    public String value;

    public Optional<Memory> getMem(){
        return mem.startsWith("mem") ? of(Utils.readString(mem+value, "mem[%n]%n", Memory.class)) : empty();
    }

}
