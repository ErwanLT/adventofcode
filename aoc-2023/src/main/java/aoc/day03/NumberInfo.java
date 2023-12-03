package aoc.day03;


import java.util.List;

public record NumberInfo(String number, List<Point> digitPositions) {

    @Override
    public String toString() {
        return "NumberInfo{" +
                "number='" + number + '\'' +
                ", digitPositions=" + digitPositions +
                '}';
    }
}
