package aoc.day17;

import aoc.Day;
import aoc.day17.shapes.*;
import aoc.day17.shapes.Shape;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Chars;

import java.awt.*;
import java.util.*;
import java.util.List;


public class Day17 implements Day {

    record RepeatingWindow(
            int startRockNumber,
            int size,
            int heightIncrease
    ) {}

    @Override
    public String part1(List<String> input) {
        return getMaxRockHeight(input.get(0), 2022);
    }

    public String getMaxRockHeight(String input, long targetRockCount) {
        var jets = Iterables.cycle(Chars.asList(input.trim().toCharArray())).iterator();
        var shapes = Iterables.cycle(Shape1.class, Shape2.class, Shape3.class, Shape4.class, Shape5.class).iterator();
        var grid = new HashSet<Point>();
        var highestY = 0;
        var heightDifferences = new ArrayList<Integer>();
        var comparedSlice = 50;
        var hashStore = new HashMap<Integer, Integer>();
        RepeatingWindow repeatingWindow;
        var recordedHeights = new HashMap<Integer, Integer>();
        var rockIndex = 0;

        while (true) {
            rockIndex++;

            Shape rock;
            int previousMaxHeight = highestY;

            try {
                rock = shapes.next().getConstructor(Point.class).newInstance(new Point(3, highestY + 4));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

            while (true) {
                if (jets.next().equals('>')) {
                    rock.moveRight(grid);
                } else {
                    rock.moveLeft(grid);
                }

                if (!rock.moveDown(grid)) {
                    for (var point: rock.getPoints()) {
                        grid.add(point);
                        highestY = Math.max(point.y, highestY);
                    }

                    break;
                }
            }

            if (rockIndex == targetRockCount) {
                return String.valueOf(highestY);
            }

            recordedHeights.put(rockIndex, highestY);
            heightDifferences.add(highestY - previousMaxHeight);

            if (heightDifferences.size() > comparedSlice) {
                var hash = heightDifferences.subList(heightDifferences.size() - comparedSlice, heightDifferences.size()).hashCode();

                if (hashStore.containsKey(hash)) {
                    repeatingWindow = new RepeatingWindow(
                            hashStore.get(hash),
                            rockIndex - hashStore.get(hash),
                            highestY - recordedHeights.get(hashStore.get(hash))
                    );
                    break;
                } else {
                    hashStore.put(hash, rockIndex);
                }
            }
        }

        var rockCountFromStartOfRepeatingWindow = targetRockCount - (repeatingWindow.startRockNumber - 1);
        var repeatingWindowCount = rockCountFromStartOfRepeatingWindow / repeatingWindow.size;
        var heightBeforeRepeatingStarts = recordedHeights.get(repeatingWindow.startRockNumber - 1);
        var maxRockHeight = heightBeforeRepeatingStarts + (repeatingWindowCount * repeatingWindow.heightIncrease);
        var countedRocks = repeatingWindow.startRockNumber - 1 + (repeatingWindowCount * repeatingWindow.size);

        var recordedHeightDifferencesIndex = repeatingWindow.startRockNumber - 1;

        while (++countedRocks <= targetRockCount) {
            maxRockHeight += heightDifferences.get(recordedHeightDifferencesIndex++);
        }

        return String.valueOf(maxRockHeight);
    }

    @Override
    public String part2(List<String> input) {
        final long LENGTH = 1000000000000L;
        return getMaxRockHeight(input.get(0), LENGTH);
    }
}
