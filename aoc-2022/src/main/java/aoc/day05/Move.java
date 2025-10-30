package aoc.day05;

public class Move {
    private long which;
    private long from;
    private long to;

    public Move(long which, long from, long to) {
        this.which = which;
        this.from = from;
        this.to = to;
    }

    public Move() {
    }

    public long getWhich() {
        return this.which;
    }

    public long getFrom() {
        return this.from;
    }

    public long getTo() {
        return this.to;
    }

    public void setWhich(long which) {
        this.which = which;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public void setTo(long to) {
        this.to = to;
    }
}
