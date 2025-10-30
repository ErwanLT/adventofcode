package aoc.day20;

import java.util.ArrayList;
import java.util.List;

public final class Pane {

    private final int id;
    private final String[][] area;

    public Pane(int id, String[][] area) {
        this.id = id;
        this.area = area;
    }

    public List<String[]> getAllBorders() {
        int n = area.length;

        List<String[]> borders = new ArrayList<>();
        borders.add(area[0]);
        borders.add(getLeftBorder());
        borders.add(getRightBorder());
        borders.add(area[n - 1]);

        return borders;
    }

    public List<String[]> getAllBordersWithFlips() {
        List<String[]> standardBorders = getAllBorders();
        List<String[]> borders = new ArrayList<>(standardBorders);
        for (String[] border : standardBorders) {
            borders.add(flipBorder(border));
        }
        return borders;
    }

    private String[] flipBorder(String[] border) {
        int n = border.length;
        String[] reversed = new String[n];
        for (int i = 0; i < n; i++) {
            reversed[n - i - 1] = border[i];
        }
        return reversed;
    }

    public String[] getRightBorder() {
        int n = area.length;
        String[] rightBorder = new String[n];
        for (int i = 0; i < n; i++) {
            rightBorder[i] = area[i][n - 1];
        }
        return rightBorder;
    }

    public String[] getBottomBorder() {
        return area[area.length - 1];
    }

    public String[] getLeftBorder() {
        int n = area.length;
        String[] leftBorder = new String[n];
        for (int i = 0; i < n; i++) {
            leftBorder[i] = area[i][0];
        }
        return leftBorder;
    }

    public String[] getTopBorder() {
        return area[0];
    }

    public Pane rotateRight() {
        int n = area.length;
        String[][] newArea = new String[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newArea[j][n - i - 1] = area[i][j];
            }
        }

        return new Pane(this.id, newArea);
    }

    public Pane flipHorizontally() {
        int n = area.length;
        String[][] newArea = new String[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newArea[n - i - 1][j] = area[i][j];
            }
        }

        return new Pane(this.id, newArea);
    }

    public void printArea() {
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area.length; j++) {
                System.out.print(area[i][j]);
            }
            System.out.println();
        }

        System.out.println();
    }

    public int countHash() {
        int count = 0;
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area.length; j++) {
                if (area[i][j].equals("#")) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Pane)) return false;
        final Pane other = (Pane) o;
        if (this.getId() != other.getId()) return false;
        if (!java.util.Arrays.deepEquals(this.getArea(), other.getArea())) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getArea());
        return result;
    }

    public String toString() {
        return "Pane(id=" + this.getId() + ", area=" + java.util.Arrays.deepToString(this.getArea()) + ")";
    }

    public int getId() {
        return this.id;
    }

    public String[][] getArea() {
        return this.area;
    }
}
