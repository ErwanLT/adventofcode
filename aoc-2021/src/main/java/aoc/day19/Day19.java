package aoc.day19;

import aoc.Day;
import aoc.parser.ParseUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day19 implements Day {

    // rotation matrixes
    static int[][] X = { { 1, 0, 0}, { 0, 0, -1}, { 0, 1, 0}, };
    static int[][] Y = { { 0, 0, 1}, { 0, 1, 0}, { -1, 0, 0}, };
    static int[][] Z = { { 0, -1, 0}, { 1, 0, 0}, { 0, 0, 1}, };

    private static List<Scanner> aligned;

    @Override
    public String part1(List<String> input) {

        var in = ParseUtils.castInputToString("\n", input);

        List<Scanner> scanners = Stream.of(in.split("--- scanner \\d+ ---")).filter(s -> !s.isBlank())
                .map(s -> parseScanner(s)).collect(Collectors.toList());
        aligned = align(scanners); // align all scanners

        Set<Vector> beacons = aligned.stream() // collect all beacons
                .flatMap(s -> s.beacons().stream()).collect(Collectors.toSet());
        return String.valueOf(beacons.size());
    }

    @Override
    public String part2(List<String> input) {
        int maxDistance = aligned.stream().flatMapToInt(a -> aligned.stream()
                .mapToInt(b -> a.location().distance(b.location()))).max().getAsInt();
        return String.valueOf(maxDistance);
    }

    static Scanner parseScanner(String s) {
        return new Scanner(Stream.of(s.split("\n")).filter(b -> !b.isBlank())
                .map(b -> parseBeacon(b.split(","))).collect(Collectors.toSet()), new Vector(0,0,0));
    }

    static Vector parseBeacon(String[] s) {
        return new Vector(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
    }

    static List<Scanner> align(List<Scanner> scanners) {
        List<Scanner> original = new ArrayList<>(scanners);
        List<Scanner> aligned = new ArrayList<>();
        aligned.add(original.remove(0));

        nextTry:
        while(!original.isEmpty()) { // Loop until all scanners aligned
            for (Scanner s : aligned) { // Use aligned scanners as source
                for (Scanner test: original) { // Test agains original list
                    for (Scanner orientation : orientations(test).toList()) { // test all orientations
                        Vector align = calculateAlignVector(s, orientation);
                        if (align != null) { // Found alignment
                            Scanner oa = orientation.move(align); // Move Scanner to origin
                            original.remove(test);
                            aligned.add(oa);
                            continue nextTry;
                        }
                    }
                }
            }
        }
        return aligned;
    }

    static Vector calculateAlignVector(Scanner s1, Scanner s2) {
        List<Vector> betweenS1S2Beacons = s1.beacons().stream() // All vectors between s1 and s2 beacons
                .flatMap(b1 -> s2.beacons().stream().filter(b2 -> b1 != b2).map(b2 -> b1.vector(b2))).toList();

        // Count number of distances for each axis
        Map<Integer,Long> x = betweenS1S2Beacons.stream().collect(Collectors.groupingBy(v -> v.x(), Collectors.counting()));
        Map<Integer,Long> y = betweenS1S2Beacons.stream().collect(Collectors.groupingBy(v -> v.y(), Collectors.counting()));
        Map<Integer,Long> z = betweenS1S2Beacons.stream().collect(Collectors.groupingBy(v -> v.z(), Collectors.counting()));

        // Get max max number of distances for each axis
        Map.Entry<Integer,Long> xMax = x.entrySet().stream().max((a,b) -> (int)(a.getValue()-b.getValue())).get();
        Map.Entry<Integer,Long> yMax = y.entrySet().stream().max((a,b) -> (int)(a.getValue()-b.getValue())).get();
        Map.Entry<Integer,Long> zMax = z.entrySet().stream().max((a,b) -> (int)(a.getValue()-b.getValue())).get();

        int minCount = Stream.of(xMax.getValue(), yMax.getValue(), zMax.getValue()).mapToInt(i -> i.intValue()).min().getAsInt();
        if (minCount >= 12) { // 12 or more same distances for each axis
            return new Vector(xMax.getKey(), yMax.getKey(), zMax.getKey());
        } else {
            return null;
        }
    }

    // Produce all possible orientations of Scanner
    static Stream<Scanner> orientations(Scanner s) {
        return rotateMatrix(rotateMatrix(rotateMatrix(Stream.of(s), X), Y), Z); // rotate through all axis
    }

    // Produce all variations of scanners rotated with given rotation matrix
    static Stream<Scanner> rotateMatrix(Stream<Scanner> scanners, int[][] rm) {
        return scanners.flatMap(s -> rotateMatrix(s, rm));
    }

    static Stream<Scanner> rotateMatrix(Scanner s, int[][] rm) {
        Set<Scanner> res = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            s = s.rotate(rm);
            res.add(s);
        }
        return res.stream();
    }
}
