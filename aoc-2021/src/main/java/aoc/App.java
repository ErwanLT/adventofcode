package aoc;

import aoc.day01.Day01;

import aoc.day02.Day02;
import aoc.day03.Day03;
import aoc.day04.Day04;
import aoc.day05.Day05;
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

    private static final Map<Integer, Day> DAYS;

    private static PrettyPrinter printer;

    static {
        DAYS = new HashMap<>();
        DAYS.put(1, new Day01());
        DAYS.put(2, new Day02());
        DAYS.put(3, new Day03());
        DAYS.put(4, new Day04());
        DAYS.put(5, new Day05());
        /*DAYS.put(6, new Day06());
        DAYS.put(7, new Day07());
        DAYS.put(8, new Day08());
        DAYS.put(9, new Day09());
        DAYS.put(10, new Day10());
        DAYS.put(11, new Day11());
        DAYS.put(12, new Day12());
        DAYS.put(13, new Day13());
        DAYS.put(14, new Day14());
        DAYS.put(15, new Day15());
        DAYS.put(16, new Day16());
        DAYS.put(17, new Day17());
        DAYS.put(18, new Day18());
        DAYS.put(19, new Day19());
        DAYS.put(20, new Day20());
        DAYS.put(21, new Day21());
        DAYS.put(22, new Day22());
        DAYS.put(23, new Day23());
        DAYS.put(24, new Day24());
        DAYS.put(25, new Day25());*/
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
        doDay(5, args);

    }

    private static void allDays(String[] args) {
        for (Map.Entry<Integer, Day> entry : DAYS.entrySet()) {
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
