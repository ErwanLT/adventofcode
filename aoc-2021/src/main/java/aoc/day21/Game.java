package aoc.day21;

import lombok.Builder;

public record Game(PlayerQuantum playerQuantum1, PlayerQuantum playerQuantum2, int round) {
    @Builder
    public Game {
    }

    public boolean isFinished() {
        return playerQuantum1.points() >= 21 || playerQuantum2.points() >= 21;
    }

    public Game move(int dice) {
        if (round % 2 == 1) {
            return new Game(playerQuantum1.move(dice), playerQuantum2, round + 1);
        } else {
            return new Game(playerQuantum1, playerQuantum2.move(dice), round + 1);
        }
    }
}