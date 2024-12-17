package aoc.day17;

import java.util.ArrayList;
import java.util.List;

public record Program(long A, long B, long C, List<Integer> program) {

    public List<Integer> simulateComputer() {
        List<Integer> outs = new ArrayList<>();
        long a = A;
        long b = B;
        long c = C;
        for (int i = 1; i <= program.size(); i += 2) {
            long cmd = program.get(i - 1);
            switch ((int) cmd) {
                case 0 -> a >>= computeOperand(program.get(i), a, b, c);
                case 1 -> b ^= program.get(i);
                case 2 -> b = computeOperand(program.get(i), a, b, c) % 8;
                case 3 -> { if (a != 0) i = program.get(i) - 1; }
                case 4 -> b ^= c;
                case 5 -> outs.add((int) (computeOperand(program.get(i), a, b, c) % 8));
                case 6 -> b = a >> computeOperand(program.get(i), a, b, c);
                case 7 -> c = a >> computeOperand(program.get(i), a, b, c);
                default -> throw new IllegalArgumentException("Invalid opcode: " + cmd);
            }
        }
        return outs;
    }

    private static long computeOperand(long val, long a, long b, long c) {
        return switch ((int) val) {
            case 0, 1, 2, 3 -> val;
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> throw new IllegalArgumentException("Invalid combo operand: " + val);
        };
    }
}