package aoc.day22;

import aoc.Day;
import aoc.parser.ParseUtils;
import aoc.parser.ReadFormatedString;

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
            int minX = Integer.parseInt(step.getMinX());
            int maxX = Integer.parseInt(step.getMaxX());
            int minY = Integer.parseInt(step.getMinY());
            int maxY = Integer.parseInt(step.getMaxY());
            int minZ = Integer.parseInt(step.getMinZ());
            int maxZ = Integer.parseInt(step.getMaxZ());

            if(minX >= -50 && maxX <= 50
                && minY >= -50 && maxY <= 50
                && minZ >= -50 && maxZ <= 50)  {

                for(int i = minX; i <= maxX; i++){
                    for(int j = minY; j <= maxY; j++){
                        for(int k = minZ; k <= maxZ; k++){
                            Cube c = Cube.builder()
                                    .x(i)
                                    .y(j)
                                    .z(k)
                                    .build();

                            System.out.println(c);

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
        System.out.println(cubes.size());
        int size = cubes.stream().toList().stream().filter(c -> c.state.equals("on")).collect(Collectors.toList()).size();

        return String.valueOf(size);
    }

    public void addStep(String s) {
        Step step = ReadFormatedString.readString(s, "%s x=%s..%s,y=%s..%s,z=%s..%s", Step.class);
        steps.add(step);
    }

    @Override
    public String part2(List<String> input) {
        return null;
    }
}
