package aoc.day25;

import aoc.Day;
import aoc.parser.ParseUtils;

import java.util.ArrayList;
import java.util.List;

public class Day25 implements Day {
    @Override
    public String part1(List<String> input) {
        List<char[]> map = new ArrayList<>();
        input.forEach(str -> map.add(str.toCharArray()));

        int H = map.size();
        int W = map.get(0).length;
        int step = 0;

        while (true) {
            List<Integer[]> mvEast = new ArrayList<>();

            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (map.get(i)[j] == '>' && map.get(i)[(j + 1) % W] == '.') {
                        mvEast.add(new Integer[]{i, j, i, (j + 1) % W});
                    }
                }
            }
            for (Integer[] fromTo : mvEast) {
                map.get(fromTo[0])[fromTo[1]] = '.';
                map.get(fromTo[2])[fromTo[3]] = '>';
            }

            List<Integer[]> mvSouth = new ArrayList<>();
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (map.get(i)[j] == 'v' && map.get((i + 1) % H)[j] == '.') {
                        mvSouth.add(new Integer[]{i, j, (i + 1) % H, j});
                    }
                }
            }
            for (Integer[] fromTo : mvSouth) {
                map.get(fromTo[0])[fromTo[1]] = '.';
                map.get(fromTo[2])[fromTo[3]] = 'v';
            }

            step++;
            if (mvEast.isEmpty() && mvSouth.isEmpty()) {
                return String.valueOf(step);
            }
        }
    }

    @Override
    public String part2(List<String> input) {
        return "Merry Christmas";
    }
}
