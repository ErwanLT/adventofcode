package aoc.utils;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Node {

    public int value;
    public Node next;
    public Node prev;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value + "";
    }
}
