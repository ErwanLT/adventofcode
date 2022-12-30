package aoc.day24;

import aoc.DayOld;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day24 implements DayOld {

    private static final String regexX = "add x ([-]?\\d+)";
    private static final String regexY = "add y w"
            + "add y ([-]?\\d+)";
    private static final String regexZ = "div z ([-]?\\d+)";

    @Override
    public String part1(List<String> input) {
        int[] xArr = new int[14];
        int[] yArr = new int[14];
        int[] zArr = new int[14];

        StringBuilder data = new StringBuilder();
        input.forEach(data::append);
        String dataStr = data.toString();

        Pattern patternX = Pattern.compile(regexX, Pattern.MULTILINE);
        Pattern patternY = Pattern.compile(regexY, Pattern.MULTILINE);
        Pattern patternZ = Pattern.compile(regexZ, Pattern.MULTILINE);
        Matcher matcherX = patternX.matcher(dataStr);
        Matcher matcherY = patternY.matcher(dataStr);
        Matcher matcherZ = patternZ.matcher(dataStr);

        int i = 0;
        while (matcherX.find()) {
            xArr[i] = Integer.parseInt(matcherX.group(1));
            i++;
        }
        i = 0;
        while (matcherY.find()) {
            yArr[i] = Integer.parseInt(matcherY.group(1));
            i++;
        }
        i = 0;
        while (matcherZ.find()) {
            zArr[i] = Integer.parseInt(matcherZ.group(1));
            i++;
        }

        long ans = solvePart1(xArr,yArr,zArr);

        return String.valueOf(ans);
    }

    @Override
    public String part2(List<String> input) {
        int[] xArr = new int[14];
        int[] yArr = new int[14];
        int[] zArr = new int[14];

        StringBuilder data = new StringBuilder();
        input.forEach(data::append);
        String dataStr = data.toString();

        Pattern patternX = Pattern.compile(regexX, Pattern.MULTILINE);
        Pattern patternY = Pattern.compile(regexY, Pattern.MULTILINE);
        Pattern patternZ = Pattern.compile(regexZ, Pattern.MULTILINE);
        Matcher matcherX = patternX.matcher(dataStr);
        Matcher matcherY = patternY.matcher(dataStr);
        Matcher matcherZ = patternZ.matcher(dataStr);

        int i = 0;
        while (matcherX.find()) {
            xArr[i] = Integer.parseInt(matcherX.group(1));
            i++;
        }
        i = 0;
        while (matcherY.find()) {
            yArr[i] = Integer.parseInt(matcherY.group(1));
            i++;
        }
        i = 0;
        while (matcherZ.find()) {
            zArr[i] = Integer.parseInt(matcherZ.group(1));
            i++;
        }

        long ans = solvePart2(xArr,yArr,zArr);

        return String.valueOf(ans);
    }

    public static boolean runBlock(int w, int z, int X, int Y, int Z, int prev) {
        int x = (z%26) + X;
        z = z / Z;
        x = (x != w ? 1 : 0);
        if (x == 1) {
            int y = 25 * x + 1;
            z *= y;
            y = (w + Y) * x;
            z += y;
        }
        return z == prev;
    }

    public static long solvePart1(int[] xArr, int[] yArr, int[] zArr) {
        long part1 = 0;

        Queue<State> queue = new LinkedList<>();
        queue.add(new State(13, 0, ""));
        while (!queue.isEmpty()) {
            State curState = queue.poll();
            if (curState.level == -1) {
                long val = Long.parseLong(curState.backtrace);
                part1 = Math.max(part1, val);
                continue;
            }
            int zstart = (zArr[curState.level] == 26 ? curState.znow * 26 : curState.znow / 26);

            for (int w = 1; w <= 9; w++) {
                for (int z=zstart; z<zstart+26; z++) {
                    if (runBlock(w, z, xArr[curState.level], yArr[curState.level], zArr[curState.level], curState.znow)) {
                        queue.add(new State(curState.level-1, z, w+curState.backtrace));
                    }
                }
            }
        }
        return part1;
    }

    public static long solvePart2(int[] xArr, int[] yArr, int[] zArr) {
        long part2 = Long.MAX_VALUE;

        Queue<State> queue = new LinkedList<>();
        queue.add(new State(13, 0, ""));
        while (!queue.isEmpty()) {
            State curState = queue.poll();
            if (curState.level == -1) {
                long val = Long.parseLong(curState.backtrace);
                part2 = Math.min(part2, val);
                continue;
            }
            int zstart = (zArr[curState.level] == 26 ? curState.znow * 26 : curState.znow / 26);

            for (int w = 1; w <= 9; w++) {
                for (int z=zstart; z<zstart+26; z++) {
                    if (runBlock(w, z, xArr[curState.level], yArr[curState.level], zArr[curState.level], curState.znow)) {
                        queue.add(new State(curState.level-1, z, w+curState.backtrace));
                    }
                }
            }
        }
        return part2;
    }
}
