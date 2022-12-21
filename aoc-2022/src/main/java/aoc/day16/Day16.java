package aoc.day16;

import aoc.Day;

import java.util.*;

public class Day16 implements Day {

    private ArrayList<Valve> valves = new ArrayList<>();

    @Override
    public String part1(List<String> input) {
        valves = parseInput(input);

        HashMap<String,HashMap<String,Integer>> paths = new HashMap<>();
        for(Valve v : valves) {
            LinkedList<Valve> queue = new LinkedList<>();
            queue.add(v);
            HashMap<String,Integer> dists = new HashMap<>();
            dists.put(v.name,0);
            HashSet<String> seen = new HashSet<>();
            seen.add(v.name);

            while(queue.size() > 0) {
                Valve cur = queue.poll();
                int distFrom = dists.get(cur.name);

                for(String connection : cur.connections) {
                    if(!seen.contains(connection)) {
                        seen.add(connection);
                        dists.put(connection, distFrom + 1);
                        queue.add(valves.stream().filter(x -> x.name.equals(connection)).findFirst().get());
                    }
                }
            }

            paths.put(v.name,dists);
        }

        ArrayList<Valve> nonzeroFlow = new ArrayList<>(valves.stream().filter(x -> x.flow > 0).toList());

        final int BITSET_MAX = 1 << nonzeroFlow.size();
        int[][][] dp = new int[31][nonzeroFlow.size()][BITSET_MAX];

        for(int[][] square : dp)
            for(int[] row : square)
                Arrays.fill(row,Integer.MIN_VALUE);

        for(int i = 0; i < nonzeroFlow.size(); i++) {
            int distFromStart = paths.get("AA").get(nonzeroFlow.get(i).name);
            dp[distFromStart + 1][i][1 << i] = 0;
        }

        int bestPressure = 0;
        for(int minute = 1; minute < 31; minute++) {
            for(int curPos = 0; curPos < nonzeroFlow.size(); curPos++) {
                for(int bitset = 0; bitset < BITSET_MAX; bitset++) {

                    int potentialFlow = getFlowOfBitmask(nonzeroFlow,bitset);

                    int newPressure = dp[minute-1][curPos][bitset] + potentialFlow;
                    if(newPressure > dp[minute][curPos][bitset]) {
                        dp[minute][curPos][bitset] = newPressure;
                    }

                    bestPressure = Math.max(bestPressure,newPressure);

                    if(((1 << curPos) & bitset) == 0) {
                        continue;
                    }

                    for(int other = 0; other < nonzeroFlow.size(); other++) {
                        if(((1 << other)& bitset) != 0)
                            continue;

                        int distTo = paths.get(nonzeroFlow.get(curPos).name).get(nonzeroFlow.get(other).name);

                        if(minute + distTo + 1 > 30)
                            continue;

                        int travelPressure =  dp[minute][curPos][bitset] + potentialFlow * (distTo + 1);

                        int newBitset = bitset | (1 << other);

                        if(travelPressure > dp[minute + distTo + 1][other][newBitset]) {
                            dp[minute + distTo + 1][other][newBitset] = travelPressure;
                        }
                    }
                }
            }
        }
        return Integer.toString(bestPressure);
    }

    private ArrayList<Valve> parseInput(List<String> input){
        ArrayList<Valve> valveArrayList = new ArrayList<>();
        for (String s:input) {
            String[] words = s.split(", |; | ");
            Valve v = new Valve();
            v.name = words[1];
            v.flow = Integer.parseInt(words[4].split("=")[1]);
            v.connections.addAll(Arrays.asList(words).subList(9, words.length));
            valveArrayList.add(v);
        }
        return valveArrayList;
    }

    public int getFlowOfBitmask(ArrayList<Valve> nonzero, int bitmask) {
        int flow = 0;
        for(int i = 0; i < nonzero.size(); i++) {
            if(((1 << i) & bitmask) != 0) {
                flow += nonzero.get(i).flow;
            }
        }
        return flow;
    }


    @Override
    public String part2(List<String> input) {
        valves = parseInput(input);
        HashMap<String,HashMap<String,Integer>> paths = new HashMap<>();
        for(Valve v : valves) {
            LinkedList<Valve> queue = new LinkedList<>();
            queue.add(v);
            HashMap<String,Integer> dists = new HashMap<>();
            dists.put(v.name,0);
            HashSet<String> seen = new HashSet<>();
            seen.add(v.name);

            while(queue.size() > 0) {
                Valve cur = queue.poll();
                int distFrom = dists.get(cur.name);

                for(String connection : cur.connections) {
                    if(!seen.contains(connection)) {
                        seen.add(connection);
                        dists.put(connection, distFrom + 1);
                        queue.add(valves.stream().filter(x -> x.name.equals(connection)).findFirst().get());
                    }
                }
            }

            paths.put(v.name,dists);
        }

        ArrayList<Valve> nonzeroFlow = new ArrayList<>(valves.stream().filter(x -> x.flow > 0).toList());

        final int BITSET_MAX = 1 << nonzeroFlow.size();

        int[][][] dp = new int[31][nonzeroFlow.size()][BITSET_MAX];

        for(int[][] square : dp)
            for(int[] row : square)
                Arrays.fill(row,Integer.MIN_VALUE);

        for(int i = 0; i < nonzeroFlow.size(); i++) {
            int distFromStart = paths.get("AA").get(nonzeroFlow.get(i).name);
            dp[distFromStart + 1][i][1 << i] = 0;
        }

        for(int minute = 1; minute < 27; minute++) {
            for(int curPos = 0; curPos < nonzeroFlow.size(); curPos++) {
                for(int bitset = 0; bitset < BITSET_MAX; bitset++) {

                    int potentialFlow = getFlowOfBitmask(nonzeroFlow,bitset);

                    int newPressure = dp[minute-1][curPos][bitset] + potentialFlow;
                    if(newPressure > dp[minute][curPos][bitset]) {
                        dp[minute][curPos][bitset] = newPressure;
                    }

                    if(((1 << curPos) & bitset) == 0) {
                        continue;
                    }

                    for(int other = 0; other < nonzeroFlow.size(); other++) {
                        if(((1 << other)& bitset) != 0)
                            continue;

                        int distTo = paths.get(nonzeroFlow.get(curPos).name).get(nonzeroFlow.get(other).name);

                        if(minute + distTo + 1 > 26)
                            continue;

                        int travelPressure =  dp[minute][curPos][bitset] + potentialFlow * (distTo + 1);

                        int newBitset = bitset | (1 << other);

                        if(travelPressure > dp[minute + distTo + 1][other][newBitset]) {
                            dp[minute + distTo + 1][other][newBitset] = travelPressure;
                        }
                    }
                }
            }
        }

        int bestPressure = 0;
        for(int mask1 = 1; mask1 < BITSET_MAX; mask1++) {
            for(int mask2 = 1; mask2 < BITSET_MAX; mask2++) {
                if((mask1 & mask2) != mask2)
                    continue;

                int best1 = 0;
                int best2 = 0;

                for(int i = 0; i < nonzeroFlow.size(); i++) {
                    best1 = Math.max(best1,dp[26][i][(mask1 & (~mask2))]);
                    best2 = Math.max(best2,dp[26][i][mask2]);
                }

                bestPressure = Math.max(bestPressure,best1 + best2);
            }
        }

        return Integer.toString(bestPressure);
    }

}
