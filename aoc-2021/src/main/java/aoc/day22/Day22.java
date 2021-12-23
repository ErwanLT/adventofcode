package aoc.day22;

import aoc.Day;
import aoc.parser.ReadFormatedString;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day22 implements Day {

    private static final List<Step> steps = new ArrayList<Step>();
    private static Set<Cube> cubes = new HashSet<>();

    @Override
    public String part1(List<String> input) {
        input.forEach(this::addStep);

        for (Step step : steps) {

            if(step.minX >= -50 && step.maxX <= 50
                && step.minY >= -50 && step.maxY <= 50
                && step.minZ >= -50 && step.maxZ <= 50)  {

                for(long i = step.minX; i <= step.maxX; i++){
                    for(long j = step.minY; j <= step.maxY; j++){
                        for(long k = step.minZ; k <= step.maxZ; k++){
                            Cube c = Cube.builder()
                                    .x((int)i)
                                    .y((int)j)
                                    .z((int)k)
                                    .build();


                            if (step.getCommand().equals("on")){
                                c.setState("on");
                            } else {
                                c.setState("off");
                            }

                            cubes.remove(c);
                            cubes.add(c);
                        }
                    }
                }
            }


        }
        int size = (int) cubes.stream().toList().stream().filter(c -> c.state.equals("on")).count();

        return String.valueOf(size);
    }

    public void addStep(String s) {
        Step step = ReadFormatedString.readString(s, "%s x=%n..%n,y=%n..%n,z=%n..%n", Step.class);
        steps.add(step);
    }

    @Override
    public String part2(List<String> input) {
        List<Step> steps2 = new ArrayList<>();
        for(Step step : steps) {
            Step st = new Step(step.command, step.minX, step.maxX+1, step.minY, step.maxY+1, step.minZ, step.maxZ+1);
            steps2 = steps2.stream().flatMap(s -> s.getSubCubes(st)).collect(Collectors.toCollection(ArrayList::new));
            if (st.command.equals("on")) {
                steps2.add(st);
            }
        }

        BigInteger sol = steps2.stream().map(this::cubeSize).reduce(BigInteger::add).orElse(BigInteger.ZERO);
        return String.valueOf(sol);
    }

    private BigInteger cubeSize(Step c) {
        return BigInteger.valueOf(c.maxX - c.minX).multiply(BigInteger.valueOf(c.maxY - c.minY)).multiply(BigInteger.valueOf(c.maxZ - c.minZ));
    }
}
