package aoc.day17;

import aoc.DayOld;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day17 implements DayOld {

    private static Set<Coordinates> initialState;

    @Override
    public String part1(List<String> input) {
        parseInput(input);
        int activeCube = runSixCycles(false);
        return String.valueOf(activeCube);
    }

    @Override
    public String part2(List<String> input) {
        parseInput(input);
        int activeCube = runSixCycles(true);
        return String.valueOf(activeCube);
    }

    private static void parseInput(List<String> inputLines) {
        initialState = new HashSet<>();
        for (int y = 0; y < inputLines.size(); y++) {
            String line = inputLines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#')
                    initialState.add(Coordinates.of(x, y, 0, 0));
            }
        }
    }

    private static int runSixCycles(boolean useFourthDim) {
        System.out.println("------ Running cycles : Start ------");
        Set<Coordinates> set = new HashSet<>(initialState);

        for (int cycle = 0; cycle < 6; cycle++) {
            set = runCycle(set, useFourthDim);
        }
        System.out.println("------ Running cycles : End ------");
        return set.size();

    }

    private static Set<Coordinates> runCycle(Set<Coordinates> set, boolean useFourthDim) {
        Set<Coordinates> next = new HashSet<>();
        Set<Coordinates> leftToCheck = new HashSet<>();

        for (Coordinates active : set) {
            int neighbors = getActiveNeighbors(set, active, leftToCheck, useFourthDim);
            if (neighbors == 2 || neighbors == 3)
                next.add(active);
        }
        for (Coordinates inactive : leftToCheck) {
            // If the inactive coord is in the set of active,
            // it isn't inactive and we already checked it.
            if (set.contains(inactive))
                continue;
            int neighbors = getActiveNeighbors(set, inactive, null, useFourthDim);
            if (neighbors == 3)
                next.add(inactive);
        }

        return next;
    }

    private static int getActiveNeighbors(Set<Coordinates> set, Coordinates coord, Set<Coordinates> leftToCheck, boolean useFourthDim) {
        int active = 0;
        int startW = useFourthDim ? -1 : 0;
        for (int z = -1; z <= 1; z++) {
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    // If no fourth dimension, just use 0 for w
                    for (int w = startW; useFourthDim ? w <= 1 : w == startW; w++) {
                        if (x == 0 && y == 0 && z == 0 && w == 0)
                            continue;
                        Coordinates neighbor = coord.resolve(x, y, z, w);
                        if (set.contains(neighbor))
                            active++;
                        if (leftToCheck != null)
                            leftToCheck.add(neighbor);
                    }
                }
            }
        }
        return active;
    }
}
