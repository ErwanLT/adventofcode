package aoc.day13;

import java.util.HashMap;
import java.util.Map;

public class LoveGraph extends HashMap<String, Map<String, Integer>> {

    void extend(String extraName, int constantLove) {
        if (containsKey(extraName)) {
            throw new IllegalArgumentException(
                    "Can not extend Lovegraph with \"" + extraName + "\"; as it is already in the graph");
        }
        Map<String, Integer> extraLove = new HashMap<>();
        keySet().forEach(key -> {
            get(key).put(extraName, constantLove);
            extraLove.put(key, constantLove);
        });
        put(extraName, extraLove);
    }
}
