package aoc.day19.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Helper {

    public static <T> List<List<T>> split(List<T> list, Predicate<T> splitter) {
        int length = list.size();
        return split(length, i -> splitter.test(list.get(i)), list::subList)
                .collect(Collectors.toList());
    }

    private static <T> Stream<T> split(int length, IntPredicate splitter, BiFunction<Integer, Integer, T> func) {
        List<Integer> indices = new ArrayList<>();
        indices.add(-1);
        for (int i = 0; i < length; i++) {
            if (splitter.test(i))
                indices.add(i);
        }
        indices.add(length);

        return IntStream.range(0, indices.size() - 1)
                .mapToObj(i -> func.apply(indices.get(i) + 1, indices.get(i + 1)));
    }
}
