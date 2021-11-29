package aoc.day20;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Data
@Value
@Getter
@Setter
public class Pane {

    private final int id;
    private final String[][] area;

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
}
