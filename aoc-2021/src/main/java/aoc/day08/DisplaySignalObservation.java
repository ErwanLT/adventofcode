package aoc.day08;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<DisplaySignal> getInput() {
        return this.input;
    }

    public List<DisplaySignal> getOutput() {
        return this.output;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DisplaySignalObservation)) return false;
        final DisplaySignalObservation other = (DisplaySignalObservation) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$input = this.getInput();
        final Object other$input = other.getInput();
        if (this$input == null ? other$input != null : !this$input.equals(other$input)) return false;
        final Object this$output = this.getOutput();
        final Object other$output = other.getOutput();
        if (this$output == null ? other$output != null : !this$output.equals(other$output)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DisplaySignalObservation;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $input = this.getInput();
        result = result * PRIME + ($input == null ? 43 : $input.hashCode());
        final Object $output = this.getOutput();
        result = result * PRIME + ($output == null ? 43 : $output.hashCode());
        return result;
    }

    public String toString() {
        return "DisplaySignalObservation(input=" + this.getInput() + ", output=" + this.getOutput() + ")";
    }
}
