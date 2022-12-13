package aoc.day13;

import aoc.Either;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Node {
    private Either<List<Node>, Integer> value;
}
