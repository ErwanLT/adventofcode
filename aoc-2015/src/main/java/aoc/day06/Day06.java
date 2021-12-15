package aoc.day06;

import aoc.Day;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day06 implements Day {
    @Override
    public String part1(List<String> input) {
        boolean[][] lit = new boolean[1000][1000];

        for (String line : input) {
            Matcher m = Pattern.compile("([e|n ([n|f])]) (\\d+),(\\d+) through (\\d+),(\\d+)").matcher(line);
            if(m.find()) {
                String instr = m.group(1);
                int x_s = Integer.parseInt(m.group(2));
                int y_s = Integer.parseInt(m.group(3));
                int x_e = Integer.parseInt(m.group(4));
                int y_e = Integer.parseInt(m.group(5));
                switch (instr) {
                    case "n":         // on
                        for (int x = x_s; x <= x_e; x++)
                            for (int y = y_s; y <= y_e; y++) {
                                lit[x][y] = true;
                            }
                        break;
                    case "f":  // off
                        for (int x = x_s; x <= x_e; x++)
                            for (int y = y_s; y <= y_e; y++) {
                                lit[x][y] = false;
                            }
                        break;
                    case "e":  // toggle
                        for (int x = x_s; x <= x_e; x++) {
                            for (int y = y_s; y <= y_e; y++) {
                                lit[x][y] = !lit[x][y];
                            }
                        }
                        break;
                }
            }
        }

        int lit_count = 0;
        for(int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                lit_count += lit[x][y] ? 1 : 0;
            }
        }

        return String.valueOf(lit_count);
    }

    @Override
    public String part2(List<String> input) {
        int[][] brightness = new int[1000][1000];

        for (String line : input) {
            Matcher m = Pattern.compile("([e|n ([n|f])]) (\\d+),(\\d+) through (\\d+),(\\d+)").matcher(line);
            if (m.find()) {
                String instr = m.group(1);
                int x_s = Integer.parseInt(m.group(2));
                int y_s = Integer.parseInt(m.group(3));
                int x_e = Integer.parseInt(m.group(4));
                int y_e = Integer.parseInt(m.group(5));
                switch (instr) {
                    case "n":         // on
                        for (int x = x_s; x <= x_e; x++)
                            for (int y = y_s; y <= y_e; y++) {
                                brightness[x][y] += 1;
                            }
                        break;
                    case "f":  // off
                        for (int x = x_s; x <= x_e; x++)
                            for (int y = y_s; y <= y_e; y++) {
                                brightness[x][y] -= (brightness[x][y] > 0 ? 1 : 0);
                            }
                        break;
                    case "e":  // toggle
                        for (int x = x_s; x <= x_e; x++) {
                            for (int y = y_s; y <= y_e; y++) {
                                brightness[x][y] += 2;
                            }
                        }
                        break;
                }
            }
        }

        int luminosity = 0;
        for (int x = 0; x < 1000; x++){
            for (int y = 0; y < 1000; y++) {
                luminosity += brightness[x][y];
            }
        }
        return String.valueOf(luminosity);
    }
}
