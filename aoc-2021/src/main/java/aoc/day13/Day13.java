package aoc.day13;

import aoc.Day;

import java.util.ArrayList;
import java.util.List;

public class Day13 implements Day {

    private static final List<Integer> xy = new ArrayList<>();
    private static final List<String> fold = new ArrayList<>();
    private static boolean map[][] = new boolean[2000][2000];

    @Override
    public String part1(List<String> input) {
        input.stream().filter(line -> !line.isEmpty())
                .forEach(line -> {
                    if (line.charAt(0) == 'f') {
                        line = line.replace("fold along ", "");
                        String[] cmd = line.split("=");
                        fold.add(cmd[0]);
                        xy.add(Integer.valueOf(cmd[1]));
                    }
                    else {
                        String[] cmd = line.split(",");
                        map[Integer.parseInt(cmd[1])][Integer.parseInt(cmd[0])] = true;
                    }
                });

        int part1 = 0;
        for (int i=0; i< xy.size();i++) {
            int val = xy.get(i);
            if (fold.get(i).equals("y")) {
                for (int ii = 0; ii < val; ii++) {
                    for (int jj = 0; jj < 2000; jj++) {
                        map[val - ii - 1][jj] = map[val - ii - 1][jj] || map[val + ii + 1][jj];
                        map[val + ii + 1][jj] = false;
                    }
                }
            } else {
                for (int ii = 0; ii < val; ii++) {
                    for (int jj = 0; jj < 2000; jj++) {
                        map[jj][val - ii - 1] = map[jj][val - ii - 1] || map[jj][val + ii + 1];
                        map[jj][val + ii + 1] = false;
                    }
                }
            }

            if (i == 0) {
                for (int ii = 0; ii < 2000; ii++) {
                    for (int jj = 0; jj < 2000; jj++) {
                        part1 += map[ii][jj] ? 1 : 0;
                    }
                }
            }
        }

        return String.valueOf(part1);
    }

    @Override
    public String part2(List<String> input) {
        StringBuilder code = new StringBuilder()
                .append("\n");
        for (int ii=0;ii<6;ii++) {
            for (int jj = 0; jj < 40; jj++) {
                code.append(map[ii][jj] ? " # " : "   ");
            }
            code.append("\n");
        }
        return code.toString();
    }
}
