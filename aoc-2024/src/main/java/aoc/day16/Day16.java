package aoc.day16;

import aoc.Day2024;
import aoc.Direction;
import aoc.grid.InfiniteGrid;
import aoc.location.Loc;

import java.util.*;

import static aoc.AOCUtils.recurseQueue;
import static aoc.Direction.EAST;
import static java.util.Comparator.comparingLong;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

public class Day16 extends Day2024 {

    public Day16() {
        super(16, "Reindeer Maze");
    }

    public static void main(String[] args) {
        new Day16().printParts();
    }

    @Override
    public Object part1() {
        InfiniteGrid grid = new InfiniteGrid(dayGrid());
        Loc end = grid.findAll('E').findFirst().orElseThrow();
        Map<State, Long> costMap = calculateCosts(grid);
        return score(costMap, end);
    }

    @Override
    public Object part2() {
        InfiniteGrid grid = new InfiniteGrid(dayGrid());
        Loc end = grid.findAll('E').findFirst().orElseThrow();
        Map<State, Long> costMap = calculateCosts(grid);
        long minScore = score(costMap, end);
        Set<State> minPath = costMap.keySet().stream().filter(state -> state.loc.equals(end) && costMap.get(state) == minScore).collect(toSet());
        return findBestTiles(grid, minPath, costMap).stream().map(State::loc).distinct().count();
    }

    private Set<State> findBestTiles(InfiniteGrid grid, Set<State> minPath, Map<State, Long> costMap) {
        return recurseQueue(minPath, new LinkedList<>(minPath), (states, stack, current) -> {
            long currentScore = costMap.get(current);
            range(0, 2).forEach(clockwise -> {
                Direction prevDir = current.dir.turn(clockwise == 0);
                State prevState = new State(current.loc, prevDir, 0);
                if (costMap.getOrDefault(prevState, Long.MAX_VALUE) == currentScore - 1000 && states.add(prevState)) {
                    stack.add(prevState);
                }
            });
            Loc prevLoc = current.loc.move(current.dir.opposite());
            if (grid.get(prevLoc).filter(c -> c != '#').isPresent()) {
                State prevState = new State(prevLoc, current.dir, 0);
                if (costMap.getOrDefault(prevState, Long.MAX_VALUE) == currentScore - 1 && states.add(prevState)) {
                    stack.add(prevState);
                }
            }
            return states;
        });
    }

    private long score(Map<State, Long> costMap, Loc end) {
        return costMap.entrySet().stream()
                .filter(e -> e.getKey().loc.equals(end))
                .mapToLong(Map.Entry::getValue)
                .min()
                .orElseThrow();
    }

    private Map<State, Long> calculateCosts(InfiniteGrid grid) {
        Loc start = grid.findAll('S').findFirst().orElseThrow();
        PriorityQueue<State> queue = new PriorityQueue<>(comparingLong(s -> s.score));
        queue.add(new State(start, EAST, 0));
        return recurseQueue(new HashMap<>(), queue, (costMap, stack, current) -> {
            if (costMap.getOrDefault(current, Long.MAX_VALUE) <= current.score) {
                return costMap;
            }
            costMap.put(current, current.score);
            Loc nextLoc = current.loc.move(current.dir);
            grid.get(nextLoc).filter(c -> c != '#').map(c -> new State(nextLoc, current.dir, current.score + 1)).ifPresent(queue::add);
            range(0, 2).mapToObj(clockwise -> current.dir.turn(clockwise == 0))
                    .map(dir -> new State(current.loc, dir, current.score + 1000))
                    .forEach(queue::add);
            return costMap;
        });
    }

    private static record State(Loc loc, Direction dir, long score) {
        @Override
        public boolean equals(Object o) {
            State state = (State) o;
            return Objects.equals(loc, state.loc) && dir == state.dir;
        }

        @Override
        public int hashCode() {
            return Objects.hash(loc, dir);
        }
    }
}
