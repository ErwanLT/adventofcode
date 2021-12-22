package aoc.day22;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Step {
    public String command;
    public String minX;
    public String maxX;
    public String minY;
    public String maxY;
    public String minZ;
    public String maxZ;
}
