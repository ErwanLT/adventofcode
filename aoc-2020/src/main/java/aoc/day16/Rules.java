package aoc.day16;

import lombok.Value;

@Value
public class Rules {

    public String name;
    public long lower1;
    public long upper1;
    public long lower2;
    public long upper2;

    public boolean check(long val) {
        return (val >= lower1 && val <= upper1) || (val >= lower2 && val <= upper2);
    }

    public boolean isDeparture() {
        return name.startsWith("departure");
    }
}
