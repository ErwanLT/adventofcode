package aoc.day07;

import lombok.Getter;
import lombok.Setter;

import static java.util.Arrays.stream;

@Getter
@Setter
public class Trade {

    public final Item input;
    public final Item[] output;

    public Trade(String trade) {
        String[] inputOutput = trade.split(" contain ");
        output = stream(inputOutput[1].split(", ")).map(Item::new).toArray(Item[]::new);
        input = new Item(1, inputOutput[0].replace(" bags", ""));
    }
}
