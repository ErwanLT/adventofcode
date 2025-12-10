package aoc.day10;

import aoc.Day2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 extends Day2025 {

    private final List<MachineLine> machines;

    public Day10() {
        super(10, "Factory");
        machines = Arrays.stream(dayStrings())
                .filter(s -> !s.trim().isEmpty())
                .map(MachineLine::new)
                .toList();
    }

    @Override
    public Object part1() {
        return machines.stream()
                .filter(m -> m.getLights() != null)
                .mapToLong(m -> solvePart1(m.getLights(), m.getButtons()))
                .sum();
    }

    private int solvePart1(int[] lights, List<int[]> buttons) {
        int numLights = lights.length;

        if (buttons.isEmpty()) {
            for (int l : lights) {
                if (l == 1)
                    return Integer.MAX_VALUE;
            }
            return 0;
        }

        int numButtons = buttons.size();
        int[][] matrix = buildIntMatrix(lights, buttons, numLights, numButtons);

        GaussResult gaussResult = gaussianEliminationInt(matrix, numLights, numButtons);

        if (checkInconsistencyInt(matrix, gaussResult.pivotRow, numLights, numButtons)) {
            return Integer.MAX_VALUE;
        }

        List<Integer> freeCols = findFreeVariables(gaussResult.pivotCols, numButtons);

        int minPresses = Integer.MAX_VALUE;
        int freeCount = freeCols.size();

        for (int mask = 0; mask < (1 << freeCount); mask++) {
            int[] sol = new int[numButtons];

            for (int k = 0; k < freeCount; k++) {
                if (((mask >> k) & 1) == 1)
                    sol[freeCols.get(k)] = 1;
            }

            for (int r = gaussResult.pivotRow - 1; r >= 0; r--) {
                int pivotCol = -1;
                for (int c = 0; c < numButtons; c++) {
                    if (matrix[r][c] == 1) {
                        pivotCol = c;
                        break;
                    }
                }

                if (pivotCol != -1) {
                    int value = matrix[r][numButtons];
                    for (int fc : freeCols) {
                        if (matrix[r][fc] == 1) {
                            value ^= sol[fc];
                        }
                    }
                    sol[pivotCol] = value;
                }
            }

            int sum = Arrays.stream(sol).sum();
            minPresses = Math.min(minPresses, sum);
        }

        return minPresses == Integer.MAX_VALUE ? 0 : minPresses;
    }

    @Override
    public Object part2() {
        return machines.stream()
                .filter(m -> m.getJoltages() != null)
                .mapToLong(m -> solvePart2(m.getJoltages(), m.getButtons()))
                .sum();
    }


    private int solvePart2(int[] targetJoltage, List<int[]> buttons) {
        int numCounters = targetJoltage.length;

        if (buttons.isEmpty()) {
            for (int target : targetJoltage) {
                if (target != 0)
                    return Integer.MAX_VALUE;
            }
            return 0;
        }

        int numButtons = buttons.size();

        double[][] matrix = buildDoubleMatrix(targetJoltage, buttons, numCounters, numButtons);

        GaussResult gaussResult = gaussianEliminationDouble(matrix, numCounters, numButtons);

        if (checkInconsistencyDouble(matrix, gaussResult.pivotRow, numCounters, numButtons)) {
            return Integer.MAX_VALUE;
        }

        List<Integer> freeCols = findFreeVariables(gaussResult.pivotCols, numButtons);

        int[] upperBounds = new int[numButtons];
        for (int j = 0; j < numButtons; j++) {
            int maxBound = Integer.MAX_VALUE;
            for (int i = 0; i < numCounters; i++) {
                if (buttons.get(j)[i] == 1 && targetJoltage[i] < maxBound) {
                    maxBound = targetJoltage[i];
                }
            }
            upperBounds[j] = maxBound == Integer.MAX_VALUE ? 0 : maxBound;
        }

        // Search over free variables
        int minPresses = Integer.MAX_VALUE;
        int numFreeVars = freeCols.size();

        if (numFreeVars == 0) {
            // Unique solution, check if valid
            int[] solution = new int[numButtons];
            boolean valid = true;

            for (int i = 0; i < gaussResult.pivotCols.size(); i++) {
                int col = gaussResult.pivotCols.get(i);
                double val = matrix[i][numButtons];

                if (Math.abs(val - Math.round(val)) > 1e-9 || val < -1e-9) {
                    valid = false;
                    break;
                }
                solution[col] = (int) Math.round(val);
            }

            if (valid) {
                int total = 0;
                for (int s : solution)
                    total += s;
                minPresses = total;
            }
        } else {
            // Enumerate free variable assignments (original recursive search)
            minPresses = searchFreeVariables(matrix, gaussResult.pivotCols, freeCols, upperBounds, numButtons, 0,
                    new int[numButtons]);
        }

        return minPresses == Integer.MAX_VALUE ? 0 : minPresses;
    }

    private int searchFreeVariables(double[][] matrix, List<Integer> pivotCols, List<Integer> freeCols,
            int[] upperBounds, int numButtons, int freeVarIndex, int[] solution) {
        if (freeVarIndex == freeCols.size()) {
            // Compute pivot variables
            for (int i = 0; i < pivotCols.size(); i++) {
                int pivotCol = pivotCols.get(i);
                double val = matrix[i][numButtons];

                // Subtract contributions from free variables
                for (int freeCol : freeCols) {
                    val -= matrix[i][freeCol] * solution[freeCol];
                }

                // Check if valid integer and non-negative
                if (Math.abs(val - Math.round(val)) > 1e-9 || val < -1e-9) {
                    return Integer.MAX_VALUE;
                }
                solution[pivotCol] = (int) Math.round(val);
            }

            // Calculate total presses
            int total = 0;
            for (int s : solution)
                total += s;
            return total;
        }

        int freeCol = freeCols.get(freeVarIndex);
        int minResult = Integer.MAX_VALUE;

        for (int val = 0; val <= upperBounds[freeCol]; val++) {
            solution[freeCol] = val;
            int result = searchFreeVariables(matrix, pivotCols, freeCols, upperBounds, numButtons, freeVarIndex + 1,
                    solution);
            minResult = Math.min(minResult, result);
        }

        return minResult;
    }

    /**
     * Builds the augmented matrix for part1 (int-based, binary operations)
     */
    private int[][] buildIntMatrix(int[] target, List<int[]> buttons, int numRows, int numButtons) {
        int[][] matrix = new int[numRows][numButtons + 1];

        for (int j = 0; j < numButtons; j++) {
            int[] b = buttons.get(j);
            for (int i = 0; i < numRows; i++) {
                matrix[i][j] = b[i];
            }
        }

        for (int i = 0; i < numRows; i++) {
            matrix[i][numButtons] = target[i];
        }

        return matrix;
    }

    /**
     * Builds the augmented matrix for part2 (double-based, real arithmetic)
     */
    private double[][] buildDoubleMatrix(int[] target, List<int[]> buttons, int numRows, int numButtons) {
        double[][] matrix = new double[numRows][numButtons + 1];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numButtons; j++) {
                matrix[i][j] = buttons.get(j)[i];
            }
            matrix[i][numButtons] = target[i];
        }

        return matrix;
    }

    /**
     * Performs Gaussian elimination on an integer matrix (GF(2) - binary field)
     */
    private GaussResult gaussianEliminationInt(int[][] matrix, int numRows, int numButtons) {
        int pivotRow = 0;
        List<Integer> pivotCols = new ArrayList<>();

        for (int col = 0; col < numButtons && pivotRow < numRows; col++) {
            int row = pivotRow;
            while (row < numRows && matrix[row][col] == 0) {
                row++;
            }

            if (row < numRows) {
                // Swap rows
                int[] tmp = matrix[row];
                matrix[row] = matrix[pivotRow];
                matrix[pivotRow] = tmp;

                // Eliminate column (XOR for binary)
                for (int r = 0; r < numRows; r++) {
                    if (r != pivotRow && matrix[r][col] == 1) {
                        for (int c = col; c <= numButtons; c++) {
                            matrix[r][c] ^= matrix[pivotRow][c];
                        }
                    }
                }

                pivotCols.add(col);
                pivotRow++;
            }
        }

        return new GaussResult(pivotRow, pivotCols);
    }

    /**
     * Performs Gaussian elimination on a double matrix (RREF with partial pivoting)
     */
    private GaussResult gaussianEliminationDouble(double[][] matrix, int numRows, int numButtons) {
        int pivotRow = 0;
        List<Integer> pivotCols = new ArrayList<>();

        for (int col = 0; col < numButtons && pivotRow < numRows; col++) {
            // Find pivot (partial pivoting)
            int maxRow = pivotRow;
            for (int row = pivotRow + 1; row < numRows; row++) {
                if (Math.abs(matrix[row][col]) > Math.abs(matrix[maxRow][col])) {
                    maxRow = row;
                }
            }

            if (Math.abs(matrix[maxRow][col]) < 1e-9) {
                continue; // No pivot in this column
            }

            // Swap rows
            double[] temp = matrix[maxRow];
            matrix[maxRow] = matrix[pivotRow];
            matrix[pivotRow] = temp;

            // Scale pivot row
            double pivot = matrix[pivotRow][col];
            for (int j = col; j <= numButtons; j++) {
                matrix[pivotRow][j] /= pivot;
            }

            // Eliminate column
            for (int row = 0; row < numRows; row++) {
                if (row != pivotRow && Math.abs(matrix[row][col]) > 1e-9) {
                    double factor = matrix[row][col];
                    for (int j = col; j <= numButtons; j++) {
                        matrix[row][j] -= factor * matrix[pivotRow][j];
                    }
                }
            }

            pivotCols.add(col);
            pivotRow++;
        }

        return new GaussResult(pivotRow, pivotCols);
    }

    /**
     * Checks if the integer matrix system is inconsistent (no solution)
     */
    private boolean checkInconsistencyInt(int[][] matrix, int pivotRow, int numRows, int numButtons) {
        for (int r = pivotRow; r < numRows; r++) {
            if (matrix[r][numButtons] == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the double matrix system is inconsistent (no solution)
     */
    private boolean checkInconsistencyDouble(double[][] matrix, int pivotRow, int numRows, int numButtons) {
        for (int row = pivotRow; row < numRows; row++) {
            if (Math.abs(matrix[row][numButtons]) > 1e-9) {
                return true;
            }
        }
        return false;
    }

    /**
     * Identifies free variables (columns without pivots)
     */
    private List<Integer> findFreeVariables(List<Integer> pivotCols, int numButtons) {
        List<Integer> freeCols = new ArrayList<>();
        for (int col = 0; col < numButtons; col++) {
            if (!pivotCols.contains(col)) {
                freeCols.add(col);
            }
        }
        return freeCols;
    }

    /**
     * Result of Gaussian elimination containing pivot information
     */
    private record GaussResult(int pivotRow, List<Integer> pivotCols) {
    }
}
