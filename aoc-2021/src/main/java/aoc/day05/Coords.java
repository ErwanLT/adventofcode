package aoc.day05;

public class Coords {

    private long x1;
    private long y1;
    private long x2;
    private long y2;

    public Coords(long x1, long y1, long x2, long y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public long getX1() {
        return this.x1;
    }

    public long getY1() {
        return this.y1;
    }

    public long getX2() {
        return this.x2;
    }

    public long getY2() {
        return this.y2;
    }

    public void setX1(long x1) {
        this.x1 = x1;
    }

    public void setY1(long y1) {
        this.y1 = y1;
    }

    public void setX2(long x2) {
        this.x2 = x2;
    }

    public void setY2(long y2) {
        this.y2 = y2;
    }
}
