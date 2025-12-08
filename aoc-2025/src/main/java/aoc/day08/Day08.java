package aoc.day08;

import aoc.Day2025;
import aoc.location.Loc3D;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day08 extends Day2025 {

    private record Edge(int u, int v, double distance) {}

    private static class DisjointSet {
        private final int[] parent;
        private final int[] sz;
        @Getter
        private int count;

        public DisjointSet(int n) {
            this.count = n;
            parent = new int[n];
            sz = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                sz[i] = 1;
            }
        }

        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]);
        }

        public boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI != rootJ) {
                if (sz[rootI] < sz[rootJ]) {
                    parent[rootI] = rootJ;
                    sz[rootJ] += sz[rootI];
                } else {
                    parent[rootJ] = rootI;
                    sz[rootI] += sz[rootJ];
                }
                count--;
                return true;
            }
            return false;
        }

        public List<Integer> getSizes() {
            Map<Integer, Integer> rootSizes = new HashMap<>();
            for (int i = 0; i < parent.length; i++) {
                int root = find(i);
                rootSizes.put(root, sz[root]);
            }
            return new ArrayList<>(rootSizes.values());
        }

    }

    private final List<Loc3D> points;
    private final List<Edge> edges;

    public Day08() {
        super(8, "Playground");

        this.points = dayStream().map(line -> {
            String[] parts = line.split(",");
            return new Loc3D(
                    Integer.parseInt(parts[0].trim()),
                    Integer.parseInt(parts[1].trim()),
                    Integer.parseInt(parts[2].trim())
            );
        }).collect(Collectors.toList());

        this.edges = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double distance = points.get(i).distance(points.get(j));
                edges.add(new Edge(i, j, distance));
            }
        }
        this.edges.sort(Comparator.comparingDouble(e -> e.distance));
    }


    @Override
    public Object part1() {
        DisjointSet dsu = new DisjointSet(points.size());
        for (int i = 0; i < 1000; i++) {
            Edge edge = edges.get(i);
            dsu.union(edge.u, edge.v);
        }

        List<Integer> sizes = dsu.getSizes();
        sizes.sort(Collections.reverseOrder());

        return sizes.get(0) * (long)sizes.get(1) * sizes.get(2);
    }

    @Override
    public Object part2() {
        DisjointSet dsu = new DisjointSet(points.size());
        for (Edge edge : edges) {
            if (dsu.union(edge.u, edge.v)) {
                if (dsu.getCount() == 1) {
                    Loc3D p1 = points.get(edge.u);
                    Loc3D p2 = points.get(edge.v);
                    return p1.x * p2.x;
                }
            }
        }
        return null;
    }
}
