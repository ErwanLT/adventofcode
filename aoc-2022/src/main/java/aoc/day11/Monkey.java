package aoc.day11;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Monkey {
    private long n;
    private String items;
    private char op;
    private String add;
    private long divisible;
    private long ifTrue;
    private long ifFalse;
}