package aoc.day02;

import aoc.Day;
import aoc.parser.ParseUtils;

import java.util.List;
import java.util.stream.Stream;

import static aoc.parser.ReadFormatedString.readString;

public class Day02 implements Day {

    private Stream<Game> inputs;

    @Override
    public String part1(List<String> input) {
        inputs = input.stream().map(String::trim).map(s -> readString(s, "%s %s", Game.class));
        return String.valueOf(inputs.mapToLong(Game::getScore1).sum());
    }

    @Override
    public String part2(List<String> input) {

        inputs = input.stream().map(String::trim).map(s -> readString(s, "%s %s", Game.class));
        return String.valueOf(inputs.mapToLong(Game::getScore2).sum());
    }
}
