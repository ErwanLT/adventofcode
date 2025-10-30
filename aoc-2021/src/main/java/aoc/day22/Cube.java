package aoc.day22;

import java.util.Objects;

public class Cube {
    int x;
    int y;
    int z;

    String state;

    public Cube(int x, int y, int z, String state) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.state = state;
    }

    public static CubeBuilder builder() {
        return new CubeBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cube)) return false;
        Cube cube = (Cube) o;
        return getX() == cube.getX() && getY() == cube.getY() && getZ() == cube.getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public String getState() {
        return this.state;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String toString() {
        return "Cube(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", state=" + this.getState() + ")";
    }

    public static class CubeBuilder {
        private int x;
        private int y;
        private int z;
        private String state;

        CubeBuilder() {
        }

        public CubeBuilder x(int x) {
            this.x = x;
            return this;
        }

        public CubeBuilder y(int y) {
            this.y = y;
            return this;
        }

        public CubeBuilder z(int z) {
            this.z = z;
            return this;
        }

        public CubeBuilder state(String state) {
            this.state = state;
            return this;
        }

        public Cube build() {
            return new Cube(this.x, this.y, this.z, this.state);
        }

        public String toString() {
            return "Cube.CubeBuilder(x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", state=" + this.state + ")";
        }
    }
}
