package aoc.day07;

import aoc.Day2024;

import java.math.BigInteger;
import java.util.Arrays;

public class Day07 extends Day2024 {

    private final String[] input;

    public Day07(){
        super(7, "Bridge Repair");
        input = dayStrings();
    }

    public static void main(String[] args) {
        new Day07().printParts();
    }

    @Override
    public Object part1() {
        return calculateTotalCalibration(false);
    }

    @Override
    public Object part2() {
        return calculateTotalCalibration(true);
    }

    /**
     * Calculates the total calibration for part1 or part2 based on the `concat` flag.
     *
     * @param concat If true, includes concatenation logic; otherwise, it doesn't.
     * @return The total calibration as a BigInteger.
     */
    private BigInteger calculateTotalCalibration(boolean concat) {
        BigInteger totalCalibration = BigInteger.ZERO;

        for (String equation : input) {
            String[] parts = equation.split(": ");
            BigInteger target = new BigInteger(parts[0]);
            BigInteger[] numbers = Arrays.stream(parts[1].split(" "))
                    .map(BigInteger::new)
                    .toArray(BigInteger[]::new);

            boolean canEvaluate = concat
                    ? canEvaluateToTargetWithConcat(numbers, 0, numbers[0], target)
                    : canEvaluateToTarget(numbers, 0, numbers[0], target);

            if (canEvaluate) {
                totalCalibration = totalCalibration.add(target);
            }
        }

        return totalCalibration;
    }

    /**
     * Recursively checks if the target value can be achieved by inserting operators.
     *
     * @param numbers The array of numbers.
     * @param index The current index in the numbers array.
     * @param currentValue The current evaluated value.
     * @param target The target value to achieve.
     * @return True if the target can be achieved, false otherwise.
     */
    private static boolean canEvaluateToTarget(BigInteger[] numbers, int index, BigInteger currentValue, BigInteger target) {
        // Base case: if we've used all numbers, check if the current value equals the target
        if (index == numbers.length - 1) {
            return currentValue.equals(target);
        }

        // Try adding the next number
        if (canEvaluateToTarget(numbers, index + 1, currentValue.add(numbers[index + 1]), target)) {
            return true;
        }

        // Try multiplying by the next number
        return canEvaluateToTarget(numbers, index + 1, currentValue.multiply(numbers[index + 1]), target);
    }

    /**
     * Recursively checks if the target value can be achieved using `+`, `*`, and `||`.
     *
     * @param numbers The array of numbers.
     * @param index The current index in the numbers array.
     * @param currentValue The current evaluated value.
     * @param target The target value to achieve.
     * @return True if the target can be achieved, false otherwise.
     */
    private static boolean canEvaluateToTargetWithConcat(BigInteger[] numbers, int index, BigInteger currentValue, BigInteger target) {
        // Base case: if we've used all numbers, check if the current value equals the target
        if (index == numbers.length - 1) {
            return currentValue.equals(target);
        }

        // Try adding the next number
        if (canEvaluateToTargetWithConcat(numbers, index + 1, currentValue.add(numbers[index + 1]), target)) {
            return true;
        }

        // Try multiplying by the next number
        if (canEvaluateToTargetWithConcat(numbers, index + 1, currentValue.multiply(numbers[index + 1]), target)) {
            return true;
        }

        // Try concatenating the next number
        BigInteger concatenatedValue = concatenate(currentValue, numbers[index + 1]);
        return canEvaluateToTargetWithConcat(numbers, index + 1, concatenatedValue, target);
    }

    /**
     * Concatenates two BigInteger numbers.
     *
     * @param left The left number.
     * @param right The right number.
     * @return The concatenated result as a BigInteger.
     */
    private static BigInteger concatenate(BigInteger left, BigInteger right) {
        String concatenatedString = left.toString() + right.toString();
        return new BigInteger(concatenatedString);
    }
}
