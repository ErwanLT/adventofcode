package aoc;

import com.eletutour.printer.PrettyPrinter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class Day {

    protected final PrettyPrinter printer;

    public static final String DEFAULT_DELIMITER = "\n";
    protected final int year;
    protected final int day;
    protected final String name;
    protected int example = 0;

    private Object solutionPart1;
    private Object solutionPart2;

    public Day(int year, int day){
        this(year, day, "");
    }

    public Day(int year, int day, String name) {
        this.year = year;
        this.day = day;
        this.name = name;
        this.printer = new PrettyPrinter();
    }

    public Day setExample(int example) {
        this.example = example;
        return this;
    }

    public static String getResourceAsString(String resource) {

        var inputStream = ClassLoader.getSystemResourceAsStream(resource);

        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    public static File getResource(String path) {
        return new File("src/main/resources/"+path);
    }

    private String getDayPath() {
        boolean b = example != 0;
        return year + (b ? "-examples" : "") + "/day" + day + (b ? "-" + example : "") + ".txt";
    }

    public abstract Object part1();

    public abstract Object part2();

    public void printParts() {
        printer.printInfo("Start : " + name);
        solutionPart1 = part1();
        if(solutionPart1 instanceof Optional) solutionPart1 = ((Optional<?>)solutionPart1).get();
        printer.printSuccess("Part 1 answer : " + solutionPart1);
        solutionPart2 = part2();
        if(solutionPart2 instanceof Optional) solutionPart2 = ((Optional<?>)solutionPart2).get();
        printer.printSuccess("Part 2 answer : " + solutionPart2);
        printer.printInfo("End");
    }

    public void printParts(int example) {
        this.example = example;
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    protected String day() {
        return getResourceAsString(getDayPath());
    }

    protected String[] dayStrings() {
        return dayStrings(DEFAULT_DELIMITER);
    }

    protected String[] dayStrings(String delimiter) {
        return Arrays.stream(day().split(delimiter)).toArray(String[]::new);
    }

    protected Stream<String> dayStream() {
        return dayStream(DEFAULT_DELIMITER);
    }

    protected Stream<String> dayStream(String delimiter) {
        return Arrays.stream(day().split(delimiter));
    }

    protected IntStream dayIntStream() {
        return dayIntStream(DEFAULT_DELIMITER);
    }

    protected IntStream dayIntStream(String delimiter) {
        return Arrays.stream(day().split(delimiter)).mapToInt(Integer::parseInt);
    }

    protected long[] dayNumbers() {
        return dayNumbers(DEFAULT_DELIMITER);
    }

    protected long[] dayNumbers(String delimiter) {
        return dayNumberStream(delimiter).toArray();
    }

    protected long[] dayDigits() {
        return day().chars().filter(n -> n >= '0' && n <= '9').mapToLong(n -> n - '0').toArray();
    }

    protected double[] dayDoubles() {
        return dayDoubles(DEFAULT_DELIMITER);
    }

    protected double[] dayDoubles(String delimiter) {
        return dayStream(delimiter).mapToDouble(Double::parseDouble).toArray();
    }

    protected LongStream dayNumberStream() {
        return dayNumberStream(DEFAULT_DELIMITER);
    }

    protected LongStream dayNumberStream(String delimiter) {
        return dayStream(delimiter).filter(e -> !e.isEmpty()).map(e -> e.replace("\n", "").trim()).mapToLong(Long::parseLong);
    }

    protected char[][] dayGrid() {
        return dayGrid(DEFAULT_DELIMITER);
    }

    protected char[][] dayGrid(String delimiter) {
        return dayStream(delimiter).map(String::toCharArray).toArray(char[][]::new);
    }
}
