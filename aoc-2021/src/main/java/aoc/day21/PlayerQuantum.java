package aoc.day21;

public record PlayerQuantum(int points, int position) {
    public PlayerQuantum {
    }

    public static PlayerQuantumBuilder builder() {
        return new PlayerQuantumBuilder();
    }

    public PlayerQuantum move(int diceValue) {
        var newPosition = (position + diceValue - 1) % 10 + 1;
        var newPoints = points + newPosition;
        return new PlayerQuantum(newPoints, newPosition);
    }

    public static class PlayerQuantumBuilder {
        private int points;
        private int position;

        PlayerQuantumBuilder() {
        }

        public PlayerQuantumBuilder points(int points) {
            this.points = points;
            return this;
        }

        public PlayerQuantumBuilder position(int position) {
            this.position = position;
            return this;
        }

        public PlayerQuantum build() {
            return new PlayerQuantum(this.points, this.position);
        }

        public String toString() {
            return "PlayerQuantum.PlayerQuantumBuilder(points=" + this.points + ", position=" + this.position + ")";
        }
    }
}
