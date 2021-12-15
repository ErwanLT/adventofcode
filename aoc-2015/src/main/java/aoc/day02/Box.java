package aoc.day02;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@Value
@AllArgsConstructor
public class Box {

    public int l;
    public int w;
    public int h;

    public int getRequiredWrappingPaper(){
        int wrappingPaper =  2*(l*w) + 2*(w*h) + 2*(h*l);
        int slack = Math.min(Math.min(l*w,w*h), h*l);
        return wrappingPaper + slack;
    }

    public int getRibbonLength(){
        int wrappingPresent = 0;
        if(l >= w && l >= h){
            wrappingPresent += 2 * w + 2 * h;
        }else if(w >= h && w >= l){
            wrappingPresent += 2 * l + 2 * h;
        }else if(h >= l && h >= w){
            wrappingPresent += 2 * w + 2 * l;
        }
        int bow = l * w * h;
        return wrappingPresent + bow;
    }
}
