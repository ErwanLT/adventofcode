package aoc.day21;

import lombok.Builder;

public record PlayerQuantum(int points, int position) {
    @Builder
    public PlayerQuantum {
    }

    public PlayerQuantum move(int diceValue) {
        var newPosition = (position + diceValue - 1) % 10 + 1;
        var newPoints = points + newPosition;
        return new PlayerQuantum(newPoints, newPosition);
    }
}
