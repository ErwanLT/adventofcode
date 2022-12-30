package aoc.day21;

import aoc.DayOld;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 implements DayOld {

    private static int player1Start = 0;
    private static int player2Start = 0;
    private static final Dice dice = new Dice();
    private static Player player1;
    private static Player player2;

    private Map<Game, BigDecimal> games;
    private final Map<Integer, Integer> diceValues = Map.of(
            3, 1,
            4, 3,
            5, 6,
            6, 7,
            7, 6,
            8, 3,
            9, 1
    );


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

        var game = Game.builder()
                .playerQuantum1(PlayerQuantum.builder().position(player1Start).build())
                .playerQuantum2(PlayerQuantum.builder().position(player2Start).build())
                .round(1)
                .build();

        games = new HashMap<>(Map.of(game, BigDecimal.valueOf(1L)));
        play();
        return String.valueOf(Math.max(wonGamesForPlayer1(), wonGamesForPlayer2()));
    }

    public void playUntilPlayerHas1000Points() {
        int pointsToWin = 1000;
        while (player1.move(dice.rollTreeTimes()) < pointsToWin && player2.move(dice.rollTreeTimes()) < pointsToWin) {
            // empty
        }
    }

    public void play() {
        while (games.keySet().stream().anyMatch(game -> !game.isFinished())) {
            var newGames = new HashMap<Game, BigDecimal>();
            for (Game game : games.keySet()) {
                playGame(game, newGames);
            }
            games = newGames;
        }
    }

    private void playGame(Game game, Map<Game, BigDecimal> newGames) {
        var currentCount = games.get(game);

        if (game.isFinished()) {
            newGames.merge(game, currentCount, BigDecimal::add);
            return;
        }

        diceValues.forEach((dice, count) -> newGames.merge(game.move(dice), currentCount.multiply(BigDecimal.valueOf(count)), BigDecimal::add));
    }


    public long wonGamesForPlayer1() {
        return games.entrySet().stream().filter(entry -> entry.getKey().playerQuantum1().points() >= 21).mapToLong(entry -> entry.getValue().longValue()).sum();
    }

    public long wonGamesForPlayer2() {
        return games.entrySet().stream().filter(entry -> entry.getKey().playerQuantum2().points() >= 21).mapToLong(entry -> entry.getValue().longValue()).sum();
    }
}
