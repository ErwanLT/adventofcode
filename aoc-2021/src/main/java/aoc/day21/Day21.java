package aoc.day21;

import aoc.Day;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day21 implements Day {

    private static int player1Start = 0;
    private static int player2Start = 0;
    private static final Dice dice = new Dice();
    private static Player player1;
    private static Player player2;

    @Override
    public String part1(List<String> input) {

        var pos = input.get(0).split(":");
        player1Start = Integer.parseInt(pos[1].trim());

        pos = input.get(1).split(":");
        player2Start = Integer.parseInt(pos[1].trim());

        player1 = Player.builder().position(player1Start).build();
        player2 = Player.builder().position(player2Start).build();

        playUntilPlayerHas1000Points();

        int minScore = Math.min(player1.getPoints(), player2.getPoints());

        return String.valueOf(minScore * dice.rolls);
    }

    @Override
    public String part2(List<String> input) {
        player1 = Player.builder().position(player1Start).build();
        player2 = Player.builder().position(player2Start).build();
        return null;
    }

    public void playUntilPlayerHas1000Points() {
        int pointsToWin = 1000;
        while (player1.move(dice.rollTreeTimes()) < pointsToWin && player2.move(dice.rollTreeTimes()) < pointsToWin) {
            // empty
        }
    }
}
