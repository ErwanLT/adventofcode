package aoc.day22;

import aoc.Day;
import aoc.Direction;
import aoc.Pair;
import aoc.grid.CharGrid;
import aoc.grid.InfiniteGrid;
import aoc.location.Loc;
import aoc.location.Range;
import aoc.parser.ParseUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static aoc.Direction.*;

public class Day22 implements Day {

    public record Me (Loc l, Direction d) {}
    public record Location(int c, Direction d) {}
    public record Instruction(Location go, boolean translate) {}
    Map<Integer, Range> cubes = Map.of(
            5, new Range(50, 0, 99, 49)/*5*/,
            6, new Range(100, 0, 149, 49)/*6*/,
            4, new Range(50, 50, 99, 99)/*4*/,
            3, new Range(50, 100, 99, 149)/*3*/,
            2, new Range(0, 100, 49, 149)/*2*/,
            1, new Range(0, 150, 49, 199)/*1*/
    );
    Map<Location, Instruction> todo = Map.of(
                    new Location(6, EAST), new Instruction(new Location(3, EAST), true), // A
                    new Location(6, SOUTH), new Instruction(new Location(4, EAST), false), // B
                    new Location(6, NORTH), new Instruction(new Location(1, SOUTH), false), // C
                    new Location(4, WEST), new Instruction(new Location(2, NORTH), false), // D
                    new Location(3, SOUTH), new Instruction(new Location(1, EAST), false), // E
                    new Location(5, NORTH), new Instruction(new Location(1, WEST), false), // F
                    new Location(5, WEST), new Instruction(new Location(2, WEST), true)  // G
            ).entrySet().stream()
            .flatMap(e -> Stream.of(Pair.of(e.getKey(), e.getValue()), Pair.of(e.getValue().go, new Instruction(e.getKey(), e.getValue().translate))))
            .collect(Collectors.toMap(Pair::a, Pair::b));

    @Override
    public String part1(List<String> input) {
        var password = solve(false, input);
        return String.valueOf(password);
    }

    @Override
    public String part2(List<String> input) {
        var password = solve(true, input);
        return String.valueOf(password);
    }

    public long solve (boolean cube, List<String> input) {
        String inputString = ParseUtils.castInputToString("\n", input);
        String[] in = inputString.split("\n\n");
        CharGrid grid = new CharGrid(in[0]);
        InfiniteGrid g = new InfiniteGrid(grid.grid);
        String instr = in[1].trim();
        Me me = new Me(g.grid.keySet().stream().filter(l -> grid.get(l) == '.' && l.y == 0).findFirst().get(), EAST);
        while(!instr.isEmpty()) {
            if(instr.charAt(0) == 'L' || instr.charAt(0) == 'R') {
                me = new Me(me.l, me.d.turn(instr.charAt(0) == 'R'));
                instr = instr.substring(1);
            } else {
                int i;
                for(i=0; i<instr.length() && Character.isDigit(instr.charAt(i)); i++);
                int n = Integer.parseInt(instr.substring(0, i));
                instr = instr.substring(i);
                for(i = 0; i<n; i++) {
                    Loc newLoc = me.d.move(me.l);
                    var atPos = g.get(newLoc);
                    if(atPos.isPresent() && atPos.get() == '.') {
                        me = new Me(newLoc, me.d);
                    } else if(!atPos.isPresent()) {
                        Me newMe = cube ? moveCubic(me) : move(g, me);
                        if(g.get(newMe.l).get() == '.') {
                            me = newMe;
                        }
                    }
                }
            }
        }
        return (1000 * (me.l.y+1)) + (4 * (me.l.x + 1)) + me.d.ordinal() - 1 + (me.d == NORTH ? 4 : 0);
    }

    public Me move(InfiniteGrid g, Me me) {
        Direction opposite = me.d.opposite();
        Loc newLoc = me.l;
        for(; g.get(newLoc).isPresent(); newLoc = opposite.move(newLoc));
        newLoc = me.d.move(newLoc);
        return new Me(newLoc, me.d);
    }

    public Me moveCubic(Me me) {
        int cube = cubes.entrySet().stream().filter(e -> e.getValue().inRange(me.l)).mapToInt(Map.Entry::getKey).findFirst().getAsInt();
        var tele = todo.get(new Location(cube, me.d));
        Range oldCube = cubes.get(cube);
        Range newCube = cubes.get(tele.go.c);
        long diff =  me.d.diagonal() ? me.l.x - oldCube.start.x : me.l.y - oldCube.start.y;
        long newX = tele.go.d == WEST ? newCube.start.x : tele.go.d == EAST ? newCube.end.x : tele.translate ? newCube.end.x - diff : newCube.start.x + diff;
        long newY = tele.go.d == NORTH ? newCube.start.y : tele.go.d == SOUTH ? newCube.end.y : tele.translate ? newCube.end.y - diff : newCube.start.y + diff;
        return new Me(new Loc(newX, newY), tele.go.d.opposite());
    }
}
