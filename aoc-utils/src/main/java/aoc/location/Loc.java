package aoc.location;

import java.awt.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;

public class Loc {
    public final long x;
    public final long y;

    public Loc() {
        this(0, 0);
    }

    public Loc(Loc p) {
        this(p.x, p.y);
    }

    public Loc(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Loc(Point p) {
        this(p.x, p.y);
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public Loc move(int dx, int dy) {
        return new Loc(x + dx, y + dy);
    }

    public Loc move(Loc l) {
        return new Loc(x + l.x, y + l.y);
    }

    public Point getPoint() {
        return new Point(intX(), intY());
    }

    public static Stream<Loc> range(int i, int j){
        return IntStream.range(0, i).boxed().flatMap(x -> IntStream.range(0, j).mapToObj(y -> new Loc(x, y)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loc loc = (Loc) o;
        if (x != loc.x) return false;
        return y == loc.y;
    }

    @Override
    public int hashCode() {
        int result = (int) (x ^ (x >>> 32));
        result = 31 * result + (int) (y ^ (y >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }

    public int intX() {
        return toIntExact(x);
    }

    public int intY() {
        return toIntExact(y);
    }
}
