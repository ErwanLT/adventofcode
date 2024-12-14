package aoc.day14;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Robot {
    public int x;
    public int y; // Position
    public int vx;
    public int vy; // Velocity
}