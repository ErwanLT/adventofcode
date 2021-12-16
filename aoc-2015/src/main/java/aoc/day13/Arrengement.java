package aoc.day13;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Arrengement {
    public final List<String> sitting = new CircularArrayList<>();
    public final Set<String> sitters = new HashSet<>();

    Arrengement(String init) {
        sitters.add(init);
        sitting.add(init);
    }

    Arrengement(Arrengement copy, String extension, int position) {
        if (copy.sitters.contains(extension)) {
            throw new IllegalArgumentException("this extension: \"" + extension + "\" is already at the table");
        }
        this.sitters.addAll(copy.sitters);
        this.sitting.addAll(copy.sitting);
        this.sitters.add(extension);
        this.sitting.add(position, extension);
    }

    int size() {
        return sitting.size();
    }

    int loveScore(LoveGraph loveGraph) {
        if (sitting.size() < 2) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < sitting.size(); ++i) {
            String left = sitting.get(i - 1);
            String middle = sitting.get(i);
            String right = sitting.get(i + 1);
            sum += loveGraph.get(middle).get(left);
            sum += loveGraph.get(middle).get(right);
        }
        return sum;
    }
}
