package aoc.day12;

public final class Flight {
    public final char dir;
    public final int distance;

    public Flight(char dir, int distance) {
        this.dir = dir;
        this.distance = distance;
    }

    public char getDir() {
        return this.dir;
    }

    public int getDistance() {
        return this.distance;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Flight)) return false;
        final Flight other = (Flight) o;
        if (this.getDir() != other.getDir()) return false;
        if (this.getDistance() != other.getDistance()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getDir();
        result = result * PRIME + this.getDistance();
        return result;
    }

    public String toString() {
        return "Flight(dir=" + this.getDir() + ", distance=" + this.getDistance() + ")";
    }
}
