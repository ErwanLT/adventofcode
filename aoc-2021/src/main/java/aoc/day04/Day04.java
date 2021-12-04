package aoc.day04;

import aoc.Day;
import aoc.ParseUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day04 implements Day {
    @Override
    public String part1(List<String> input) {
        String in = ParseUtils.castInputToString(input);
        String[] split = in.split("\n\n");
        long[] numbers = Arrays.stream(split[0].split(",")).mapToLong(Long::parseLong).toArray();
        List<BingoGrid> cards = IntStream.range(1, split.length).mapToObj(i -> split[i]).map(BingoGrid::new).collect(Collectors.toList());

        for(long num : numbers){
            for(BingoGrid card : cards){
                if(markCard(card, num) && checkCard(card)){
                    return String.valueOf(result(card, num));
                }
            }
        }
        return "";
    }

    @Override
    public String part2(List<String> input) {
        String in = ParseUtils.castInputToString(input);
        String[] split = in.split("\n\n");
        long[] numbers = Arrays.stream(split[0].split(",")).mapToLong(Long::parseLong).toArray();
        List<BingoGrid> cards = IntStream.range(1, split.length).mapToObj(i -> split[i]).map(BingoGrid::new).collect(Collectors.toList());

        for(long num : numbers){
            for(int i = 0; i<cards.size(); i++){
                BingoGrid card = cards.get(i);
                if(markCard(card, num) && checkCard(card)){
                    if(cards.size() == 1){
                        return String.valueOf(result(card, num));
                    } else {
                        cards.remove(i);
                        i--;
                    }
                }
            }
        }
        return "";
    }

    private long result(BingoGrid card, long num) {
        return card.sumExcept(-1) * num;
    }

    private boolean markCard(BingoGrid card, long num){
        return card.replace(num, -1);
    }

    private boolean checkCard(BingoGrid grid){
        long[][] card = grid.grid;
        for(long[] nums : card){
            if(Arrays.stream(nums).allMatch(n -> n==-1)){
                return true;
            }
        }
        out: for(int i = 0; i<card[0].length; i++){
            for(int j = 0; j<card[i].length; j++){
                if(card[j][i] != -1){
                    continue out;
                }
            }
            return true;
        }
        return false;
    }
}
