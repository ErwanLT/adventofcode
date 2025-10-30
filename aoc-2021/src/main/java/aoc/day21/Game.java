package aoc.day21;

public record Game(PlayerQuantum playerQuantum1, PlayerQuantum playerQuantum2, int round) {
    public Game {
    }

    public static GameBuilder builder() {
        return new GameBuilder();
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

    public static class GameBuilder {
        private PlayerQuantum playerQuantum1;
        private PlayerQuantum playerQuantum2;
        private int round;

        GameBuilder() {
        }

        public GameBuilder playerQuantum1(PlayerQuantum playerQuantum1) {
            this.playerQuantum1 = playerQuantum1;
            return this;
        }

        public GameBuilder playerQuantum2(PlayerQuantum playerQuantum2) {
            this.playerQuantum2 = playerQuantum2;
            return this;
        }

        public GameBuilder round(int round) {
            this.round = round;
            return this;
        }

        public Game build() {
            return new Game(this.playerQuantum1, this.playerQuantum2, this.round);
        }

        public String toString() {
            return "Game.GameBuilder(playerQuantum1=" + this.playerQuantum1 + ", playerQuantum2=" + this.playerQuantum2 + ", round=" + this.round + ")";
        }
    }
}