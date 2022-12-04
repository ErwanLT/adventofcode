package aoc.day04;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    private long aStart;
    private long aEnd;
    private long bStart;
    private long bEnd;


    public boolean contained(){
        return (aStart>=bStart && aEnd<=bEnd) || (bStart>=aStart && bEnd<=aEnd);
    }

    public boolean overlap(){
        return aStart <= bEnd && aEnd >= bStart;
    }
}
