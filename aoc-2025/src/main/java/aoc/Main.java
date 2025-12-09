package aoc;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (int day = 1; day <11; day++){
            System.out.println("Day " + day + ":");
            String paddedDay = String.valueOf(day);
            if(day < 10) {
                paddedDay = "0" + day;
            }
            Day instance = (Day) Class.forName("aoc.day" + paddedDay + ".Day"+paddedDay).getDeclaredConstructor().newInstance();
            instance.printParts();
            System.out.println();
        }
    }
}
