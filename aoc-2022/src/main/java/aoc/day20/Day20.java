package aoc.day20;

import aoc.CircularList;
import aoc.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day20 implements Day {

    public record Number(long value){};

    @Override
    public String part1(List<String> input) {
        ArrayList<NumWrapper> nums = new ArrayList<NumWrapper>();
        NumWrapper zero = null;
        for(int i = 0; i < input.size(); i++) {
            nums.add(new NumWrapper(Integer.parseInt(input.get(i)), i));
            if(Integer.parseInt(input.get(i)) == 0)
                zero = new NumWrapper(Integer.parseInt(input.get(i)),i);
        }
        mix(nums, new ArrayList<NumWrapper>(nums));
        int startIndex = nums.indexOf(zero);
        return Long.toString(nums.get((startIndex + 1000) % nums.size()).num + nums.get((startIndex + 2000) % nums.size()).num + nums.get((startIndex + 3000) % nums.size()).num);
    }

    public void mix(ArrayList<NumWrapper> nums, ArrayList<NumWrapper> orig) {
        for(NumWrapper i : orig) {
            int oldIndex = nums.indexOf(i);
            nums.remove(oldIndex);
            //floorMod is good for ensuring the sign ends up correct
            int newIndex = Math.floorMod(oldIndex + i.num,nums.size());
            nums.add(newIndex,i);
        }
    }

    @Override
    public String part2(List<String> input) {
        final long key = 811589153;
        ArrayList<NumWrapper> nums = new ArrayList<>();
        NumWrapper zero = null;
        for(int i = 0; i < input.size(); i++) {
            int val = Integer.parseInt(input.get(i));
            nums.add(new NumWrapper(val * key, i));
            if(val == 0)
                zero = new NumWrapper(val,i);
        }
        ArrayList<NumWrapper> orig = new ArrayList<>(nums);
        for(int i = 0; i < 10; i++) {
            mix(nums,orig);
        }
        int startIndex = nums.indexOf(zero);
        return Long.toString(nums.get((startIndex + 1000) % nums.size()).num + nums.get((startIndex + 2000) % nums.size()).num + nums.get((startIndex + 3000) % nums.size()).num);
    }
}
