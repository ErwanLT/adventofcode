package aoc.day21;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Player {
    public int points;
    public int position;

    public int move(int diceValue) {
        position = (position + diceValue - 1) % 10 + 1;
        points += position;
        return points;
    }
}
