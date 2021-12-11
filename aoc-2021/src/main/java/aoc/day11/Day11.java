package aoc.day11;

import aoc.Day;
import aoc.parser.ParseUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Day11 implements Day {

    private static int[][] map;

    @Override
    public String part1(List<String> input) {
        map = ParseUtils.castInputToBiIntarray(input);

        long count = IntStream.range(0, 100)
                .mapToLong(i -> step(map)).sum();

        return String.valueOf(count);
    }

    @Override
    public String part2(List<String> input) {
        int step = 100;
        while(!allFlashes(map)) {
            step(map);
            step++;
        }
        return String.valueOf(step);
    }


    private static long step(int[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                map[y][x]++;
            }
        }
        long count = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                count += flash(map, x, y);
            }
        }
        return count;
    }

    private static long flash(int[][] map, int x, int y) {
        if (map[y][x] < 10) { // only flash 10 or larger
            return 0;
        }
        long count = 1;
        map[y][x] = 0; // reset to 0 after flashing
        for (int nx = max(0, x-1); nx <= min(x+1, map[y].length-1); nx++) {
            for (int ny = max(0, y-1); ny <= min(y+1, map.length-1); ny++) {
                if (map[ny][nx] == 0) { // can only flash once per step, 0 is already flashed
                    continue;
                }
                map[ny][nx]++;
                count += flash(map, nx, ny);
            }
        }
        return count;
    }

    private static boolean allFlashes(int[][] map) {
        return Arrays.stream(map).flatMapToInt(Arrays::stream)
                .noneMatch(v -> v != 0);
    }
}
