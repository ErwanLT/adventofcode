package aoc.day08;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DisplaySignalObservation {
    private final List<DisplaySignal> input;
    private final List<DisplaySignal> output;

    public DisplaySignalObservation(String value) {
        String[] inputOutput = value.split("\\|");

        this.input = readSignals(inputOutput[0]);
        this.output = readSignals(inputOutput[1]);
    }

    private List<DisplaySignal> readSignals(String value) {
        return Arrays.stream(value.trim().split(" "))
                .map(DisplaySignal::new)
                .collect(Collectors.toList());
    }
}
