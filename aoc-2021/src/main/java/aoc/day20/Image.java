package aoc.day20;

import java.util.Set;

public record Image(boolean background, Set<Pixel> pixels, int x1, int y1, int x2, int y2) {
    static Image create(boolean background, Set<Pixel> pixels) {
        int minY = pixels.stream().mapToInt(p -> p.y()).min().getAsInt();
        int minX = pixels.stream().mapToInt(p -> p.x()).min().getAsInt();
        int maxY = pixels.stream().mapToInt(p -> p.y()).max().getAsInt();
        int maxX = pixels.stream().mapToInt(p -> p.x()).max().getAsInt();
        return new Image(background, pixels, minX, minY, maxX, maxY);
    }
    boolean bit(int x, int y) {
        if (x < x1 || x > x2 || y < y1 || y > y2) {
            return background; // changing background for out of pixel bits
        }
        return pixels.contains(new Pixel(x, y));
    }
}
