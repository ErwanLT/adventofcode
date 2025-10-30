package aoc.day03;

import aoc.DayOld;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 implements DayOld {

    private record Claim(int id, int x, int y, int width, int height) {}

    private List<Claim> parseClaims(List<String> input) {
        List<Claim> claims = new ArrayList<>();
        Pattern pattern = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");

        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                claims.add(new Claim(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)),
                        Integer.parseInt(matcher.group(5))
                ));
            }
        }
        return claims;
    }

    private int[][] buildFabric(List<Claim> claims) {
        int[][] fabric = new int[1000][1000];
        for (Claim claim : claims) {
            for (int i = claim.x; i < claim.x + claim.width; i++) {
                for (int j = claim.y; j < claim.y + claim.height; j++) {
                    fabric[i][j]++;
                }
            }
        }
        return fabric;
    }

    @Override
    public String part1(List<String> input) {
        List<Claim> claims = parseClaims(input);
        int[][] fabric = buildFabric(claims);

        long overlappingInches = 0;
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (fabric[i][j] > 1) {
                    overlappingInches++;
                }
            }
        }
        return String.valueOf(overlappingInches);
    }

    @Override
    public String part2(List<String> input) {
        List<Claim> claims = parseClaims(input);
        int[][] fabric = buildFabric(claims);

        for (Claim claim : claims) {
            boolean overlaps = false;
            for (int i = claim.x; i < claim.x + claim.width; i++) {
                for (int j = claim.y; j < claim.y + claim.height; j++) {
                    if (fabric[i][j] > 1) {
                        overlaps = true;
                        break;
                    }
                }
                if (overlaps) {
                    break;
                }
            }
            if (!overlaps) {
                return String.valueOf(claim.id());
            }
        }
        return "No non-overlapping claim found";
    }
}
