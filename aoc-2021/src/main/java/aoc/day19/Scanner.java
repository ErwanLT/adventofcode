package aoc.day19;

import java.util.Set;
import java.util.stream.Collectors;

public record Scanner(Set<Vector> beacons, Vector location) {
    Scanner rotate(int[][] matrix) {
        return new Scanner(beacons.stream().map(b -> b.rotate(matrix)).collect(Collectors.toSet()), location);
    }
    Scanner move(Vector location) {
        return new Scanner(beacons.stream().map(b -> b.move(location)).collect(Collectors.toSet()), location);
    }
}
