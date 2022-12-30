package aoc.day04;

import aoc.DayOld;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Day04 implements DayOld {

    private static String in;

    @Override
    public String part1(List<String> input) {
        in = input.get(0);
        long index = 0L;
        try {
           index = getMD5Hash("00000");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return String.valueOf(index-1);
    }

    @Override
    public String part2(List<String> input) {
        long index = 0L;
        try {
            index = getMD5Hash("000000");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return String.valueOf(index-1);
    }

    private static long getMD5Hash(String s) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        long index = 0L;
        while (true) {
            String key = String.format("%s%d", in, index++);
            byte[] digest = md.digest(key.getBytes());
            if (byteToHexString(digest).startsWith(s)) {
                break;
            }
        }
        return index;
    }

    private static String byteToHexString(byte[] digest) {
        String result = "";
        for (byte b : digest) {
            result += Integer.toString((b & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}
