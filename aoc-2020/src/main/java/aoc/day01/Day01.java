package aoc.day01;

import aoc.DayOld;

import java.util.List;

public class Day01 implements DayOld {
    @Override
    public String part1(List<String> input) {

        int number1 = 0;
        int number2 = 0;

        for(int i = 0; i< input.size(); i++){
            for(int j = 1; j < input.size(); j++){
                int num1 = Integer.parseInt(input.get(i));
                int num2 = Integer.parseInt(input.get(j));
                if(num1 + num2 == 2020){
                    number1 = num1;
                    number2 = num2;
                    break;
                }
            }
        }
        return String.valueOf(number1 * number2);

    }

    @Override
    public String part2(List<String> input) {

        int number1 = 0;
        int number2 = 0;
        int number3 = 0;

        for(int i = 0; i< input.size(); i++){
            for(int j = 1; j < input.size(); j++){
                for(int k = 2; k < input.size(); k++){

                    int num1 = Integer.parseInt(input.get(i));
                    int num2 = Integer.parseInt(input.get(j));
                    int num3 = Integer.parseInt(input.get(k));
                    
                    if(num1 + num2 + num3 == 2020){
                        number1 = num1;
                        number2 = num2;
                        number3 = num3;
                        break;
                    }
                }

            }
        }

        return String.valueOf(number1 * number2 * number3);
        
    }
}
