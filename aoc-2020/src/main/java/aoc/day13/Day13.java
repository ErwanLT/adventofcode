package aoc.day13;

import aoc.Day;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class Day13 implements Day {

    @Override
    public String part1(List<String> input) {
        int timestamp = Integer.parseInt(input.get(0));
        String[] stimes = input.get(1).split(",");
        List<Integer> t = new ArrayList<>();
        for(String time : stimes){
            if(!time.equals("x")){
                t.add(Integer.valueOf(time));
            }
        }
        int[] times = t.stream().mapToInt(i->i).toArray();

        for(int i = timestamp; true; i++){
            for(int j : times){
                if(i%j == 0){
                    return String.valueOf(j * (i - timestamp));
                }
            }
        }
    }

    @Override
    public String part2(List<String> input) {
        String[] s = input.get(1).split(",");
        long[][] nums = range(0, s.length).filter(i -> !s[i].equals("x"))
                .mapToObj(i -> new long[]{parseLong(s[i]), i})
                .toArray(long[][]::new);
        long product = stream(nums).mapToLong(a -> a[0]).reduce((a, b) -> a * b).getAsLong();
        long sum = stream(nums).mapToLong(a -> a[1] * (product/a[0]) * inverseModulo(product/a[0], a[0])).sum();
        return String.valueOf(product - sum % product);
    }

    private static long inverseModulo(long x, long y){
        if(x!=0){
            long modulo = y % x;
            return modulo == 0 ? 1 : y - inverseModulo(modulo, x) * y / x;
        }
        return 0;
    }
}
