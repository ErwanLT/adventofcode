package aoc.day17;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    public Point topLeft;
    public Point bottomRight;

    public boolean inArea(Point p){
        return p.x>=topLeft.x && p.y >= topLeft.y && p.x <=bottomRight.x && p.y <= bottomRight.y;
    }
}
