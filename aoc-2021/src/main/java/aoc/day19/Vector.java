package aoc.day19;

public record Vector(int x, int y, int z) {
    Vector rotate(int[][] rm) {
        int rx = rm[0][0]*x + rm[0][1]*y + rm[0][2]*z;
        int ry = rm[1][0]*x + rm[1][1]*y + rm[1][2]*z;
        int rz = rm[2][0]*x + rm[2][1]*y + rm[2][2]*z;
        return new Vector(rx, ry, rz);
    }
    Vector move(Vector move) {
        return new Vector(x+move.x, y+move.y, z+move.z);
    }
    Vector vector(Vector b) {
        return new Vector(x-b.x, y-b.y, z-b.z);
    }
    int distance(Vector b) {
        return Math.abs((x-b.x)) + Math.abs((y-b.y)) + Math.abs((z-b.z));
    }
}
