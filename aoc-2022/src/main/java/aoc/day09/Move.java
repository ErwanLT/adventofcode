package aoc.day09;

public class Move {
    private char dir;
    private long n;

    public Move(char dir, long n) {
        this.dir = dir;
        this.n = n;
    }

    public char getDir() {
        return this.dir;
    }

    public long getN() {
        return this.n;
    }

    public void setDir(char dir) {
        this.dir = dir;
    }

    public void setN(long n) {
        this.n = n;
    }
}
