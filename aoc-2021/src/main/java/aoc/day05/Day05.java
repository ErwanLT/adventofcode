package aoc.day05;

import aoc.Day;
import aoc.ReadFormatedString;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day05 implements Day {

    private static List<Coords> coords;
    private static Set<Point> all = new HashSet<>();
    private static Set<Point> vis = new HashSet<>();

    @Override
    public String part1(List<String> input) {
        coords = input.stream().map(e -> ReadFormatedString.readString(e, "%n,%n -> %n,%n", Coords.class)).collect(Collectors.toList());
        coords.forEach( c ->{
            long x1 = c.getX1();
            long x2 = c.getX2();
            long y1 = c.getY1();
            long y2 = c.getY2();

            if(x1 == x2){
                for(long y = Math.min(y1, y2); y<=Math.max(y1, y2); y++){
                    var l = new Point(Math.toIntExact(x1), Math.toIntExact(y));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            } else if(y1 == y2){
                for(long x = Math.min(x1, x2); x<=Math.max(x1, x2); x++){
                    var l = new Point(Math.toIntExact(x), Math.toIntExact(y1));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            }
        });

        return String.valueOf(vis.size());
    }

    @Override
    public String part2(List<String> input) {
        all = new HashSet<>();
        vis = new HashSet<>();
        coords.forEach( c ->{
            long x1 = c.getX1();
            long x2 = c.getX2();
            long y1 = c.getY1();
            long y2 = c.getY2();

            if(x1 == x2){
                for(long y = Math.min(y1, y2); y<=Math.max(y1, y2); y++){
                    var l = new Point(Math.toIntExact(x1), Math.toIntExact(y));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            } else if(y1 == y2){
                for(long x = Math.min(x1, x2); x<=Math.max(x1, x2); x++){
                    var l = new Point(Math.toIntExact(x), Math.toIntExact(y1));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            } else if((x1 > x2 && y1 > y2) || (x1 < x2 && y1 < y2)){
                for(long x = 0; x<=Math.max(x1, x2)-Math.min(x1, x2); x++){
                    var l = new Point(Math.toIntExact(Math.min(x1, x2)+x), Math.toIntExact(Math.min(y1, y2)+x));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            }else if(x1 < x2 && y1 > y2){
                for(long x = 0; x<=Math.max(x1, x2)-Math.min(x1, x2); x++){
                    var l = new Point(Math.toIntExact(x1+x), Math.toIntExact(y1-x));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            }else if(x1 > x2 && y1 < y2){
                for(long x = 0; x<=Math.max(x1, x2)-Math.min(x1, x2); x++){
                    var l = new Point(Math.toIntExact(x1-x), Math.toIntExact(y1+x));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            }
        });
        return String.valueOf(vis.size());
    }
}
