package aoc.day10;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
public class MachineLine{

    private int[] lights;
    private int[] joltages;
    private List<int[]> buttons;


    public MachineLine(String line) {
        this.lights = parseLights(line);
        this.joltages = parseJoltages(line);

        int dimension = (lights != null) ? lights.length :
                (joltages != null) ? joltages.length : 0;

       this.buttons = parseButtons(line, dimension);
    }

    private int[] parseLights(String line) {
        Matcher m = Pattern.compile("\\[([.#]+)]").matcher(line);
        if (!m.find()) return null;

        String s = m.group(1);
        int[] arr = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            arr[i] = (s.charAt(i) == '#') ? 1 : 0;
        }
        return arr;
    }

    private int[] parseJoltages(String line) {
        Matcher m = Pattern.compile("\\{([^}]+)}").matcher(line);
        if (!m.find()) return null;

        return Arrays.stream(m.group(1).split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private List<int[]> parseButtons(String line, int dimension) {
        List<int[]> list = new ArrayList<>();
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(line);

        while (m.find()) {
            int[] button = new int[dimension];
            for (String idx : m.group(1).split(",")) {
                int i = Integer.parseInt(idx.trim());
                if (i < dimension) button[i] = 1;
            }
            list.add(button);
        }

        return list;
    }

}