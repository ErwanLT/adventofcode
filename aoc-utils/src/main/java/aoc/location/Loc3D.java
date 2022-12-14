package aoc.location;

import java.util.List;

public class Loc3D {
    public final long x;
    public final long y;
    public final long z;

    public Loc3D() {
        this(0, 0, 0);
    }

    public Loc3D(Loc3D p) {
        this(p.x, p.y, p.z);
    }

    public Loc3D(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Loc3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Loc3D(long[] l) {
        this.x = l[0];
        this.y = l[1];
        this.z = l[2];
    }

    public List<Long> toList(){
        return List.of(x,y,z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Loc3D move(long dx, long dy, long dz) {
        return new Loc3D(x + dx, y + dy, z + dz);
    }

    public double distance(Loc3D p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2) + Math.pow(z - p.getZ(), 2));
    }

    public Loc3D distanceTo(Loc3D b) {
        return new Loc3D(b.x - x, b.y - y, b.z - z);
    }

    public Loc3D flip(int flip) {
        return switch (flip) {
            case 0 -> this;
            case 1 -> new Loc3D(x, -y, -z);
            case 2 -> new Loc3D(x, -z, y);
            case 3 -> new Loc3D(-y, -z, x);
            case 4 -> new Loc3D(-x, -z, -y);
            case 5 -> new Loc3D(y, -z, -x);
            default -> throw new IllegalStateException("Invalid flip value: "+flip);
        };
    }

    public Loc3D rotate(int rot) {
        return switch (rot) {
            case 0 -> this;
            case 1 -> new Loc3D(-y, x, z);
            case 2 -> new Loc3D(-x, -y, z);
            case 3 -> new Loc3D(y, -x, z);
            default -> throw new IllegalStateException("Invalid rotation value: "+rot);
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loc3D loc = (Loc3D) o;
        return x == loc.x && y == loc.y && z == loc.z;
    }


    @Override
    public String toString() {
        return "Loc3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public boolean sameDistance(Loc3D m, Loc3D n) {
        return (x + m.x) == n.x && (y + m.y) == n.y && (z + m.z) == n.z;
    }
}
