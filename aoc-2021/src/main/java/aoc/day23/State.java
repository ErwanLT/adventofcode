package aoc.day23;

import java.util.List;
import java.util.stream.Stream;

public record State(List<List<String>> rooms, List<String> hallway) {
    int roomSize() {
        return amphipods().mapToInt(a -> a.charAt(1) - '0').max().getAsInt();
    }
    Stream<String> amphipodsInRooms() {
        return rooms.stream().flatMap(r -> r.stream()).filter(a -> a.length() > 1);
    }
    Stream<String> amphipods() {
        return Stream.concat(hallway.stream().filter(a -> a.length() > 1), amphipodsInRooms());
    }
}
