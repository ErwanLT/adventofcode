package aoc.day13;

import aoc.Either;

import java.util.List;

public class Node {
    private Either<List<Node>, Integer> value;

    public Node(Either<List<Node>, Integer> value) {
        this.value = value;
    }

    public Either<List<Node>, Integer> getValue() {
        return this.value;
    }

    public void setValue(Either<List<Node>, Integer> value) {
        this.value = value;
    }
}
