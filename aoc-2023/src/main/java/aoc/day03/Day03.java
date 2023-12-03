package aoc.day03;

import aoc.Day2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day03 extends Day2023 {

    public Day03() {
        super(3);
    }

    public static void main(String[] args) {
        new Day03().printParts();
    }

    @Override
    public Object part1() {
        var matrix = dayGrid();
        var numbers = concatenateNumbers(matrix);

        var numberToSum = new ArrayList<>(numbers);

        for (NumberInfo numberInfo : numbers) {
            List<Point> pointForVerification = new ArrayList<>();

            System.out.println("Number: " + numberInfo.number());
            System.out.println("Digit Positions: " + numberInfo.digitPositions());
            var digitPoints = numberInfo.digitPositions();

            for (Point point : numberInfo.digitPositions()) {
                System.out.println(point.surroundingPoints());
                pointForVerification.addAll(point.surroundingPoints());
            }

            // Utiliser un ensemble pour garantir l'unicité des points
            Set<Point> uniquePointsForVerification = new HashSet<>(pointForVerification);
            digitPoints.forEach(uniquePointsForVerification::remove);

            System.out.println("Point for verification: " + uniquePointsForVerification);
            System.out.println();

            boolean containsOnlyDots = uniquePointsForVerification.stream()
                    .filter(point -> matrixContains(matrix, point))
                    .allMatch(point -> matrix[point.x][point.y] == '.');

            if (containsOnlyDots) {
                numberToSum.remove(numberInfo);
            }
        }

        return numberToSum.stream()
                .map(NumberInfo::number)
                .mapToInt(Integer::parseInt)
                .sum();

    }

    private static List<NumberInfo> concatenateNumbers(char[][] matrix) {
        List<NumberInfo> numbersList = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (Character.isDigit(matrix[i][j])) {
                    StringBuilder number = new StringBuilder();
                    List<Point> digitPositions = new ArrayList<>();

                    while (j < matrix[i].length && Character.isDigit(matrix[i][j])) {
                        number.append(matrix[i][j]);
                        digitPositions.add(new Point(i, j));
                        j++;
                    }

                    numbersList.add(new NumberInfo(number.toString(), digitPositions));
                }
            }
        }
        return numbersList;
    }

    private static boolean matrixContains(char[][] matrix, Point point) {
        return point.x >= 0 && point.x < matrix.length && point.y >= 0 && point.y < matrix[0].length;
    }

    @Override
    public Object part2() {
        List<Integer> numbersWithCommonStar = findNumbersWithCommonStar(dayGrid());
        return numbersWithCommonStar.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static List<Integer> findNumbersWithCommonStar(char[][] matrix) {
        List<Integer> numbersWithCommonStar = new ArrayList<>();

        var numbers = concatenateNumbers(matrix);

        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (haveCommonStarNeighbors(matrix, numbers.get(i), numbers.get(j))) {
                    var num1 = Integer.valueOf(numbers.get(i).number());
                    var num2 =Integer.valueOf(numbers.get(j).number());

                    numbersWithCommonStar.add(num1 * num2);
                }
            }
        }

        return numbersWithCommonStar;
    }

    private static boolean haveCommonStarNeighbors(char[][] matrix, NumberInfo number1, NumberInfo number2) {
        Set<Point> neighborsNumber1 = getStarNeighbors(matrix, number1.digitPositions());
        Set<Point> neighborsNumber2 = getStarNeighbors(matrix, number2.digitPositions());

        // Vérifier s'il y a des voisins communs '*'
        neighborsNumber1.retainAll(neighborsNumber2);

        return !neighborsNumber1.isEmpty();
    }

    private static Set<Point> getStarNeighbors(char[][] matrix, List<Point> digitPositions) {
        Set<Point> starNeighbors = new HashSet<>();

        for (Point digitPosition : digitPositions) {
            for (Point surroundingPoint : digitPosition.surroundingPoints()) {
                if (matrixContains(matrix, surroundingPoint) && matrix[surroundingPoint.x][surroundingPoint.y] == '*') {
                    starNeighbors.add(surroundingPoint);
                }
            }
        }

        return starNeighbors;
    }
}
