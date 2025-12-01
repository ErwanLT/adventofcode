package aoc.day01;

import aoc.Day2025;

import java.util.List;

public class Day01 extends Day2025 {

    private record Rotation(char direction, int distance) {}

    private final List<Rotation> rotations;

    public Day01() {
        super(1, "Secret Entrance");
        this.rotations = dayStream()
                .map(line -> new Rotation(line.charAt(0), Integer.parseInt(line.substring(1))))
                .toList();
    }

    @Override
    public Object part1() {
        int currentPosition = 50;
        int zerosCount = 0;

        for (Rotation rotation : rotations) {
            if (rotation.direction == 'R') {
                currentPosition = (currentPosition + rotation.distance) % 100;
            } else { // 'L'
                currentPosition = (currentPosition - rotation.distance % 100 + 100) % 100;
            }

            if (currentPosition == 0) {
                zerosCount++;
            }
        }

        return zerosCount;
    }

    @Override
    public Object part2() {
        int currentPosition = 50;
        int totalZerosCount = 0;

        for (Rotation rotation : rotations) {
            for (int i = 0; i < rotation.distance; i++) {
                if (rotation.direction == 'R') {
                    currentPosition = (currentPosition + 1) % 100;
                } else { // 'L'
                    currentPosition = (currentPosition - 1 + 100) % 100;
                }

                if (currentPosition == 0) {
                    totalZerosCount++;
                }
            }
        }

        return totalZerosCount;
    }
}
