package aoc.day09;

import aoc.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Day09 implements Day {

    private static int H, W;
    private static List<List<Integer>> data;
    private static boolean[][] visited;
    private static int[] dxArr = new int[]{1,-1,0,0};
    private static int[] dyArr = new int[]{0,0,1,-1};

    @Override
    public String part1(List<String> input) {

        data = new ArrayList<>();
        input.forEach(str -> {
            List<Integer> row = new ArrayList<>();
            for (int i = 0; i < str.length(); i++) {
                row.add(Integer.parseInt(String.valueOf(str.charAt(i))));
            }
            data.add(row);
        });

        H = data.size();
        W = data.get(0).size();

        int ans = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                boolean top = (i == 0 || data.get(i).get(j) < data.get(i - 1).get(j));
                boolean bot = (i == H - 1 || data.get(i).get(j) < data.get(i + 1).get(j));
                boolean left = (j == 0 || data.get(i).get(j) < data.get(i).get(j - 1));
                boolean right = (j == W - 1 || data.get(i).get(j) < data.get(i).get(j + 1));
                if (top && bot && left && right) {
                    ans += data.get(i).get(j) + 1;
                }
            }
        }

        return String.valueOf(ans);
    }

    @Override
    public String part2(List<String> input) {

        visited = new boolean[H][W];
        // too lazy to change to maxHeap
        PriorityQueue<Integer> minHeap=new PriorityQueue<>();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (!visited[i][j]) {
                    visited[i][j] = true;
                    if (data.get(i).get(j) != 9) {
                        int area = dfs(i, j);
                        minHeap.add(-area);
                    }
                }
            }
        }
        return String.valueOf(minHeap.poll() * minHeap.poll() * minHeap.poll() * -1);
    }

    public static int dfs(int i, int j) {
        int area = 1;
        for (int k=0;k<4; k++) {
            int dx = dxArr[k];
            int dy = dyArr[k];
            if (dx + i >= 0 && dx + i < H && dy + j >= 0 && dy + j < W) {
                int ii = dx + i;
                int jj = dy + j;
                if (!visited[ii][jj] && data.get(ii).get(jj) < 9) {
                    visited[ii][jj] = true;
                    area += dfs(ii, jj);
                }
            }
        }
        return area;
    }

}
