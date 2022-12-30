package aoc.day15;

import aoc.DayOld;
import aoc.parser.ParseUtils;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.BidirectionalDijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Day15 implements DayOld {

    private static int height;
    private static int width;
    private static int[][] risks;

    @Override
    public String part1(List<String> input) {

        String[] lines = ParseUtils.castInputToString("\n", input).trim().split("\n");
        height = lines.length;
        width = lines[0].length();
        risks = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                risks[y][x] = lines[y].charAt(x) - '0';
            }
        }

        Graph<Point, DefaultWeightedEdge> graph = buildGraph(false);
        Point start = new Point(0, 0);
        Point end = new Point(width - 1, height - 1);
        return String.valueOf((int) new BidirectionalDijkstraShortestPath<>(graph).getPath(start, end).getWeight());
    }

    @Override
    public String part2(List<String> input) {
        Graph<Point, DefaultWeightedEdge> graph = buildGraph(true);
        Point start = new Point(0, 0);
        Point end = new Point(5 * width - 1, 5 * height - 1);
        return String.valueOf((int) new BidirectionalDijkstraShortestPath<>(graph).getPath(start, end).getWeight());
    }

    private Graph<Point, DefaultWeightedEdge> buildGraph(boolean part2) {
        var graph = new DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        var maxX = part2 ? width * 5 : width;
        var maxY = part2 ? height * 5 : height;
        for (var y = 0; y < maxY; y++) {
            for (var x = 0; x < maxX; x++) {
                var point = new Point(x, y);
                graph.addVertex(point);
            }
        }
        for (var y = 0; y < maxY; y++) {
            for (var x = 0; x < maxX; x++) {
                var point = new Point(x, y);
                var value = (risks[y % height][x % width] + x / width + y / height - 1) % 9 + 1;
                for (var adjacent : adjacent(point, part2)) {
                    var edge = graph.addEdge(adjacent, point);
                    graph.setEdgeWeight(edge, value);
                }
            }
        }
        return graph;
    }

    private Collection<Point> adjacent(Point point, boolean part2) {
        var points = new ArrayList<Point>(4);
        points.add(new Point(point.x, point.y - 1));
        points.add(new Point(point.x, point.y + 1));
        points.add(new Point(point.x - 1, point.y));
        points.add(new Point(point.x + 1, point.y));
        if (part2) {
            points.removeIf(p -> p.x < 0 || p.x >= width * 5 || p.y < 0 || p.y >= height * 5);
        } else {
            points.removeIf(p -> p.x < 0 || p.x >= width || p.y < 0 || p.y >= height);
        }
        return points;
    }
}
