package aoc.day03;

import aoc.DayOld;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day03 implements DayOld {

    private static char[] in;

    @Override
    public String part1(List<String> input) {
        in = input.get(0).toCharArray();

        Point origine = new Point(0, 0);
        Set<Point> housesVisited = new HashSet<>();
        housesVisited.add(origine);

        int x = 0;
        int y = 0;
        for (int i = 0; i < in.length; i++){
            switch (in[i]) {
                case '^' :
                    y++;
                    break;
                case '>' :
                    x++;
                    break;
                case '<' :
                    x--;
                    break;
                case 'v' :
                    y--;
                    break;
            }
            Point coordinate = new Point(x, y);
            housesVisited.add(coordinate);
        }

        return String.valueOf(housesVisited.size());
    }

    @Override
    public String part2(List<String> input) {
        Point origine = new Point(0, 0);
        Set<Point> housesVisited = new HashSet<>();
        housesVisited.add(origine);

        int santaX = 0;
        int santaY = 0;
        int robotSantaX = 0;
        int robotSantaY = 0;
        boolean santaTurn = true;

        for (int i = 0; i < in.length; i++){
            if(santaTurn){
                santaTurn = false;
                switch (in[i]) {
                    case '^' :
                        santaY++;
                        break;
                    case '>' :
                        santaX++;
                        break;
                    case '<' :
                        santaX--;
                        break;
                    case 'v' :
                        santaY--;
                        break;
                }
                Point coordinate = new Point(santaX, santaY);
                housesVisited.add(coordinate);
            } else {
                santaTurn = true;
                switch (in[i]) {
                    case '^' :
                        robotSantaY++;
                        break;
                    case '>' :
                        robotSantaX++;
                        break;
                    case '<' :
                        robotSantaX--;
                        break;
                    case 'v' :
                        robotSantaY--;
                        break;
                }
                Point coordinate = new Point(robotSantaX, robotSantaY);
                housesVisited.add(coordinate);
            }
        }

        return String.valueOf(housesVisited.size());
    }
}
