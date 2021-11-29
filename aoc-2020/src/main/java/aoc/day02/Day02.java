package aoc.day02;

import aoc.Day;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Day02 implements Day {

    @Override
    public String part1(List<String> input) {
        List<String> correctPassword = new ArrayList<>();

        for (String line: input) {
            String minMax = line.substring(0, line.lastIndexOf(':') -1).trim();
            int min = Integer.parseInt(minMax.substring(0, minMax.lastIndexOf('-')));
            int max = Integer.parseInt(minMax.substring(minMax.lastIndexOf('-')+1));
            String letter = line.substring(line.lastIndexOf(':')-1, line.lastIndexOf(':')).trim();
            String password = line.substring(line.lastIndexOf(':') + 1).trim();

            int matches = StringUtils.countMatches(password, letter);
            if(matches>= min && matches<=max){
                correctPassword.add(password);
            }

        }

        return String.valueOf(correctPassword.size());
    }

    @Override
    public String part2(List<String> input) {

        List<String> correctPassword = new ArrayList<>();
        for (String line: input) {
            String minMax = line.substring(0, line.lastIndexOf(':') -1).trim();
            int position1 = Integer.parseInt(minMax.substring(0, minMax.lastIndexOf('-')));
            int position2 = Integer.parseInt(minMax.substring(minMax.lastIndexOf('-')+1));
            char letter = line.substring(line.lastIndexOf(':')-2, line.lastIndexOf(':')).trim().charAt(0);
            String password = line.substring(line.lastIndexOf(':') + 1).trim();

            char[] passwordArray = password.toCharArray();
            if (passwordArray[position1-1] == letter ^ passwordArray[position2-1] == letter){
                correctPassword.add(password);
            }

        }

        return String.valueOf(correctPassword.size());
    }
}
