package aoc.day02;

import aoc.DayOld;

import java.util.ArrayList;
import java.util.List;

public class Day02 implements DayOld {

    private static List<Box> boxes;

    @Override
    public String part1(List<String> input) {
        boxes = new ArrayList<>();

        for (String s : input) {
            String[] values = s.split("x");
            Box box = new Box(Integer.parseInt(values[0]),Integer.parseInt(values[1]),Integer.parseInt(values[2]));
            boxes.add(box);
        }

        int squareFeetOfWrappingPaper = 0;
        for (Box b: boxes) {
            squareFeetOfWrappingPaper += b.getRequiredWrappingPaper();
        }

        return String.valueOf(squareFeetOfWrappingPaper);
    }

    @Override
    public String part2(List<String> input) {
        int ribbonLength = 0;
        for (Box b: boxes) {
            ribbonLength += b.getRibbonLength();
        }
        return String.valueOf(ribbonLength);
    }
}
