package aoc.day21;

public class Player {
    public int points;
    public int position;

    Player(int points, int position) {
        this.points = points;
        this.position = position;
    }

    public static PlayerBuilder builder() {
        return new PlayerBuilder();
    }

    public int move(int diceValue) {
        position = (position + diceValue - 1) % 10 + 1;
        points += position;
        return points;
    }

    public int getPoints() {
        return this.points;
    }

    public int getPosition() {
        return this.position;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Player)) return false;
        final Player other = (Player) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getPoints() != other.getPoints()) return false;
        if (this.getPosition() != other.getPosition()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Player;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getPoints();
        result = result * PRIME + this.getPosition();
        return result;
    }

    public static class PlayerBuilder {
        private int points;
        private int position;

        PlayerBuilder() {
        }

        public PlayerBuilder points(int points) {
            this.points = points;
            return this;
        }

        public PlayerBuilder position(int position) {
            this.position = position;
            return this;
        }

        public Player build() {
            return new Player(this.points, this.position);
        }

        public String toString() {
            return "Player.PlayerBuilder(points=" + this.points + ", position=" + this.position + ")";
        }
    }
}
