package aoc.day12;

import aoc.Day2025;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 extends Day2025 {

    private Map<Integer, Shape> shapes;
    private List<Region> regions;

    public Day12() {
        super(12, "Christmas Tree Farm");
        parseInput();
    }

    private void parseInput() {
        if (shapes != null && regions != null) {
            return;
        }

        shapes = new HashMap<>();
        regions = new ArrayList<>();

        String input = day();

        // Split into shapes section and regions section
        // Shapes are lines starting with "digit:" followed by shape data
        // Regions are lines with "widthxheight: counts"
        String[] lines = input.split("\n");

        // Parse shapes
        Map<Integer, Set<Point>> shapePoints = new HashMap<>();
        int currentShapeId = -1;
        int row = 0;
        Pattern shapeIdPattern = Pattern.compile("^(\\d+):$");
        Pattern regionPattern = Pattern.compile("(\\d+)x(\\d+):(.*)");

        for (String line : lines) {
            // Check if it's a region line
            Matcher regionMatcher = regionPattern.matcher(line);
            if (regionMatcher.matches()) {
                // Parse region
                int width = Integer.parseInt(regionMatcher.group(1));
                int height = Integer.parseInt(regionMatcher.group(2));
                int[] counts = Arrays.stream(regionMatcher.group(3).trim().split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                regions.add(new Region(width, height, counts));
            } else {
                // Check if it's a shape ID line
                Matcher shapeIdMatcher = shapeIdPattern.matcher(line);
                if (shapeIdMatcher.matches()) {
                    currentShapeId = Integer.parseInt(shapeIdMatcher.group(1));
                    shapePoints.put(currentShapeId, new HashSet<>());
                    row = 0;
                } else if (currentShapeId != -1 && !line.isBlank()) {
                    // Parse shape data
                    for (int col = 0; col < line.length(); col++) {
                        if (line.charAt(col) == '#') {
                            shapePoints.get(currentShapeId).add(new Point(row, col));
                        }
                    }
                    row++;
                }
            }
        }

        shapePoints.forEach((id, points) -> shapes.put(id, new Shape(points)));
    }

    @Override
    public Object part1() {
        return regions.stream()
                .filter(this::canFitAllPresents)
                .count();
    }

    private boolean canFitAllPresents(Region region) {
        // Build list of all shape instances to place
        List<Integer> shapesToPlace = new ArrayList<>();
        for (int i = 0; i < region.counts().length; i++) {

            for (int j = 0; j < region.counts()[i]; j++) {
                shapesToPlace.add(i);
            }
        }

        // Quick area check first
        long requiredArea = shapesToPlace.stream()
                .mapToLong(shapeId -> shapes.get(shapeId).getSize())
                .sum();
        if (requiredArea > (long) region.width() * region.height()) {
            return false;
        }

        // Create empty grid
        boolean[][] grid = new boolean[region.height()][region.width()];

        // Try to place all shapes using backtracking
        return tryPlaceShapes(grid, shapesToPlace, 0);
    }

    private boolean tryPlaceShapes(boolean[][] grid, List<Integer> shapesToPlace, int index) {
        // Base case: all shapes placed successfully
        if (index >= shapesToPlace.size()) {
            return true;
        }

        int shapeId = shapesToPlace.get(index);
        Shape shape = shapes.get(shapeId);

        // Try each transformation of the shape
        for (Set<Point> transformation : shape.getTransformations()) {
            // Try each position in the grid
            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[0].length; c++) {
                    // Check if we can place the shape at this position
                    if (canPlaceShape(grid, transformation, r, c)) {
                        // Place the shape
                        placeShape(grid, transformation, r, c, true);

                        // Recursively try to place remaining shapes
                        if (tryPlaceShapes(grid, shapesToPlace, index + 1)) {
                            return true;
                        }

                        // Backtrack: remove the shape
                        placeShape(grid, transformation, r, c, false);
                    }
                }
            }
        }

        return false;
    }

    private boolean canPlaceShape(boolean[][] grid, Set<Point> shapePoints, int startR, int startC) {
        for (Point p : shapePoints) {
            int r = startR + p.r();
            int c = startC + p.c();

            // Check bounds
            if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) {
                return false;
            }

            // Check if position is already occupied
            if (grid[r][c]) {
                return false;
            }
        }
        return true;
    }

    private void placeShape(boolean[][] grid, Set<Point> shapePoints, int startR, int startC, boolean place) {
        for (Point p : shapePoints) {
            int r = startR + p.r();
            int c = startC + p.c();
            grid[r][c] = place;
        }
    }

    @Override
    public Object part2() {
        return "Not implemented";
    }
}
