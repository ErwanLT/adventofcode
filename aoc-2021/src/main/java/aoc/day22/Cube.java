package aoc.day22;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Cube {
    int x;
    int y;
    int z;

    String state;

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
}
