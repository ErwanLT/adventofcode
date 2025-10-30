package aoc.day17;

public class Target {
    public long xStart;
    public long xEnd;
    public long yStart;
    public long yEnd;


    public Target(long xStart, long xEnd, long yStart, long yEnd) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
    }

    public Target() {
    }

    public long getXStart() {
        return this.xStart;
    }

    public long getXEnd() {
        return this.xEnd;
    }

    public long getYStart() {
        return this.yStart;
    }

    public long getYEnd() {
        return this.yEnd;
    }

    public void setXStart(long xStart) {
        this.xStart = xStart;
    }

    public void setXEnd(long xEnd) {
        this.xEnd = xEnd;
    }

    public void setYStart(long yStart) {
        this.yStart = yStart;
    }

    public void setYEnd(long yEnd) {
        this.yEnd = yEnd;
    }
}
