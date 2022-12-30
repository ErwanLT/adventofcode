package aoc;

import aoc.day01.Day01;
import aoc.day02.Day02;
import com.eletutour.printer.PrettyPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class App {

    private static final Map<Integer, DayOld> DAYS;

    private static PrettyPrinter printer;

    static {
        DAYS = new HashMap<>();
        DAYS.put(1, new Day01());
        DAYS.put(2, new Day02());
    }

    private static List<String> loadInput(int day){
        printer.printInfo("getting input for day : "+ day);
        String paddedDay = String.valueOf(day);
        if(day < 10) {
            paddedDay = "0" + day;
        }
        String fileName = "day" + paddedDay + ".txt";

        try(BufferedReader r = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName)))){
            return r.lines().collect(toList());
        } catch(IOException e){
            throw new UncheckedIOException(e);
        }
    }

    public static void main(String[] args) {
        printer = new PrettyPrinter();

        //allDays(args);
        doDay(2, args);

    }

    private static void allDays(String[] args) {
        for (Map.Entry<Integer, DayOld> entry : DAYS.entrySet()) {
            int day = entry.getKey();

            if(args.length != 0){
                day = Integer.parseInt(args[0]);
            }

            List<String> input = loadInput(day);

            printer.printInfo("Solving part 1");
            printer.printSuccess("Part 1 answer : " + DAYS.get(day).part1(input));
            printer.printInfo("Solving part 2");
            printer.printSuccess("Part 2 answer : " + DAYS.get(day).part2(input));
        }
    }

    private static void doDay(int day, String[] args){

        if(args.length != 0){
            day = Integer.parseInt(args[0]);
        }

        List<String> input = loadInput(day);
        printer.printInfo("Solving part 1");
        printer.printSuccess("Part 1 answer : " + DAYS.get(day).part1(input));
        printer.printInfo("Solving part 2");
        printer.printSuccess("Part 2 answer : " + DAYS.get(day).part2(input));
    }
}
