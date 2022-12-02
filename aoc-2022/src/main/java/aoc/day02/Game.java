package aoc.day02;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static aoc.day02.Outcome.*;
import static aoc.day02.Shape.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private String a;
    private String b;

    private Shape getShape(String s){
        return switch (s) {
            case "A", "X" -> ROCK;
            case "B", "Y" -> PAPER;
            case "C", "Z" -> SCISSOR;
            default -> throw new RuntimeException(s);
        };
    }

    private Shape choose(Shape s, Outcome desired) {
        return Shape.values()[(s.ordinal() + desired.ordinal()) % Shape.values().length];
    }

    long getScore1() {
        return getScore(getShape(b));
    }

    private long getScore(Shape sb) {
        Shape sa = getShape(a);
        long baseScore = sb.ordinal()+1;
        return switch(calculateOutcome(sa, sb)) {
            case LOSS -> baseScore;
            case WIN -> baseScore + 6;
            case DRAW -> baseScore + 3;
        };
    }

    private Outcome calculateOutcome(Shape sa, Shape sb) {
        if(sa == sb) {
            return DRAW;
        } else if(sa.ordinal() == ((sb.ordinal() + 1) % Shape.values().length)) {
            return LOSS;
        } else return WIN;
    }

    long getScore2() {
        return getScore(choose(getShape(a), b.equals("X")? LOSS :b.equals("Y")? DRAW: WIN));
    }
}
