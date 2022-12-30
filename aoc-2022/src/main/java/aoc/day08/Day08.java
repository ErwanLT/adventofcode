package aoc.day08;

import aoc.DayOld;
import aoc.day11.Direction;
import aoc.grid.NumGrid;
import aoc.parser.ParseUtils;

import java.awt.*;
import java.util.List;

public class Day08 implements DayOld {

    @Override
    public String part1(List<String> input) {
        String str = ParseUtils.castInputToString("\n", input);
        NumGrid grid = new NumGrid(str, "\n", "");
        var count = grid.stream().filter(p -> findEdge(grid, p)).count();
        return String.valueOf(count);
    }

    private boolean findEdge(NumGrid grid, Point p) {
        Direction[] dirs = Direction.fourDirections();
        long num = grid.get(p);
        for(Direction d : dirs) {
            Point newLoc = p;
            while (true) {
                newLoc = d.move(newLoc);
                long atLoc = grid.get(newLoc);
                if(atLoc >= num) break;
                else if(atLoc == -1) return true;
            }
        }
        return false;
    }


    @Override
    public String part2(List<String> input) {
        String str = ParseUtils.castInputToString("\n", input);
        NumGrid grid = new NumGrid(str, "\n", "");
        var scenicScore = grid.stream().mapToLong(p -> scenicScore(grid, p)).max().getAsLong();
        return String.valueOf(scenicScore);
    }

    private long scenicScore(NumGrid grid, Point p) {
        Direction[] dirs = Direction.fourDirections();
        long num = grid.get(p);
        long score = 1;
        for(Direction d : dirs) {
            Point newLoc = p;
            long s = 0;
            while (true) {
                newLoc = d.move(newLoc);
                long atLoc = grid.get(newLoc);
                if(atLoc >= num) {s++; break;}
                else if(atLoc == -1) break;
                s++;
            }
            score*=s;
        }
        return score;
    }
}
