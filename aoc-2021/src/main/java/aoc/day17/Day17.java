package aoc.day17;

import aoc.Day;
import aoc.parser.ReadFormatedString;

import java.awt.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.lang.Long.MIN_VALUE;
import static java.lang.Math.toIntExact;

public class Day17 implements Day {


    @Override
    public String part1(List<String> input) {
        return String.valueOf(getSolution(input.get(0)).max().getAsLong());
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(getSolution(input.get(0)).filter(e -> e!=MIN_VALUE).count());
    }

    private static LongStream getSolution(String input){
        Target t = ReadFormatedString.readString(input, "target area: x=%n..%n, y=%n..%n", Target.class);
        Area area = new Area(new Point(toIntExact(t.xStart), toIntExact(t.yStart)), new Point(toIntExact(t.xEnd), toIntExact(t.yEnd)));
        return IntStream.range(-200, 300).boxed().flatMap(x -> IntStream.range(-200, 300).mapToObj(y -> new Point(x, y))).mapToLong(p -> simulateSteps(area, p));
    }

    private static long simulateSteps(Area target, Point p){
        Point curr = new Point(0,0);
        long highest = 0;
        while(curr.y>target.topLeft.y && !target.inArea(curr)) {
            curr = new Point(curr.x + p.x, curr.y + p.y);
            p = new Point(p.x > 0 ? p.x - 1 : (p.x < 0 ? p.x - 1 : p.x), p.y - 1);
            if(curr.y > highest) highest = curr.y;
            if(curr.x == 0 && (curr.y < target.topLeft.y || curr.y > target.bottomRight.y)) break;
        }
        if(target.inArea(curr)){
            return highest;
        }
        return MIN_VALUE;
    }

}
