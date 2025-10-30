package aoc.day22;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Step {
    public String command;
    public long minX;
    public long maxX;
    public long minY;
    public long maxY;
    public long minZ;
    public long maxZ;

    public Step(String command, long minX, long maxX, long minY, long maxY, long minZ, long maxZ) {
        this.command = command;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public Stream<Step> getSubCubes(Step s2) {
        if (s2.contains(this)) {
            return Stream.of();
        } else if (!touches(s2)) {
            return Stream.of(this);
        }

        var xIntersect = Stream.of(s2.minX, s2.maxX).filter(x -> minX < x && x < maxX).collect(Collectors.toCollection(ArrayList::new));
        var yIntersect = Stream.of(s2.minY, s2.maxY).filter(y -> minY < y && y < maxY).collect(Collectors.toCollection(ArrayList::new));
        var zIntersect = Stream.of(s2.minZ, s2.maxZ).filter(z -> minZ < z && z < maxZ).collect(Collectors.toCollection(ArrayList::new));

        xIntersect.add(0, minX);
        xIntersect.add(maxX);
        yIntersect.add(0, minY);
        yIntersect.add(maxY);
        zIntersect.add(0, minZ);
        zIntersect.add(maxZ);

        var res = new ArrayList<Step>();
        for (int i = 0; i < xIntersect.size() - 1; i++) {
            for (int j = 0; j < yIntersect.size() - 1; j++) {
                for (int k = 0; k < zIntersect.size() - 1; k++) {
                    res.add(new Step(command, xIntersect.get(i), xIntersect.get(i + 1), yIntersect.get(j), yIntersect.get(j + 1), zIntersect.get(k), zIntersect.get(k + 1)));
                }
            }
        }
        return res.stream().filter(c -> !s2.contains(c));
    }

    private boolean contains(Step s) {
        return minX <= s.minX && maxX >= s.maxX && minY <= s.minY && maxY >= s.maxY && minZ <= s.minZ && maxZ >= s.maxZ;
    }

    private boolean touches(Step s) {
        return minX <= s.maxX && maxX >= s.minX && minY <= s.maxY && maxY >= s.minY && minZ <= s.maxZ && maxZ >= s.minZ;
    }

    public String getCommand() {
        return this.command;
    }

    public long getMinX() {
        return this.minX;
    }

    public long getMaxX() {
        return this.maxX;
    }

    public long getMinY() {
        return this.minY;
    }

    public long getMaxY() {
        return this.maxY;
    }

    public long getMinZ() {
        return this.minZ;
    }

    public long getMaxZ() {
        return this.maxZ;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setMinX(long minX) {
        this.minX = minX;
    }

    public void setMaxX(long maxX) {
        this.maxX = maxX;
    }

    public void setMinY(long minY) {
        this.minY = minY;
    }

    public void setMaxY(long maxY) {
        this.maxY = maxY;
    }

    public void setMinZ(long minZ) {
        this.minZ = minZ;
    }

    public void setMaxZ(long maxZ) {
        this.maxZ = maxZ;
    }

    public String toString() {
        return "Step(command=" + this.getCommand() + ", minX=" + this.getMinX() + ", maxX=" + this.getMaxX() + ", minY=" + this.getMinY() + ", maxY=" + this.getMaxY() + ", minZ=" + this.getMinZ() + ", maxZ=" + this.getMaxZ() + ")";
    }
}
