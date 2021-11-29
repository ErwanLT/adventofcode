package aoc.day07;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

    public long amount;
    public final String item;

    public Item(String item) {
        amount = Integer.parseInt(item.substring(0,1));
        this.item = item.substring(2).replaceAll(" bag(s?)(.?)", "");
    }

    public Item(long i, String string) {
        amount = i;
        item = string;
    }
}
