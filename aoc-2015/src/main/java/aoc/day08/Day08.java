package aoc.day08;

import aoc.DayOld;

import java.util.List;

public class Day08 implements DayOld {

    private static final int doubleQuoteCount = 2;

    @Override
    public String part1(List<String> input) {
        int totalCharacterCount = input.stream().mapToInt(String::length).sum();
        int totalCharacterCountForStringLiterals = input.stream().mapToInt(Day08::getCharacterCountPt1).sum();

        return String.valueOf(totalCharacterCount - totalCharacterCountForStringLiterals);
    }

    @Override
    public String part2(List<String> input) {
        int totalCharacterCount = input.stream().mapToInt(String::length).sum();
        int totalCharacterCountForStringLiterals = input.stream().mapToInt(Day08::getCharacterCountPt2).sum();

        return String.valueOf(totalCharacterCountForStringLiterals - totalCharacterCount);
    }

    private static int getCharacterCountPt1(String str) {
        int characterCount = 0;
        int strLen = str.length();
        int currentIndex = 0;
        while(currentIndex < strLen) {
            char currentCharacter = str.charAt(currentIndex);
            if(currentCharacter == '\\' && currentIndex < strLen - 1) {
                if(str.charAt(currentIndex + 1) == '\\' || str.charAt(currentIndex + 1) == '\"') {
                    currentIndex += 2;
                } else if(str.charAt(currentIndex + 1) == 'x') {
                    currentIndex += 4;
                }
            } else {
                currentIndex++;
            }

            characterCount++;
        }

        return characterCount - doubleQuoteCount;
    }

    private static int getCharacterCountPt2(String str) {
        int characterCount = 0;
        int strLen = str.length();
        for(int i = 0; i < strLen; i++) {
            char currentChar = str.charAt(i);
            if(currentChar == '\\' || currentChar == '\"') {
                characterCount += 2;
            } else {
                characterCount++;
            }
        }

        return characterCount + doubleQuoteCount;
    }
}
