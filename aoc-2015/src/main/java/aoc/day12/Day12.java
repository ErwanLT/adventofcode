package aoc.day12;

import aoc.DayOld;
import aoc.parser.ParseUtils;

import javax.json.*;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 implements DayOld {

    private static String day;
    private static final Pattern NUMBER = Pattern.compile("(-?\\d+)");

    @Override
    public String part1(List<String> input) {
        day = ParseUtils.castInputToString("\n", input);

        Matcher matcher = NUMBER.matcher(day);
        int sum = 0;
        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group());
        }

        return String.valueOf(sum);
    }

    @Override
    public String part2(List<String> input) {
        JsonReader reader = Json.createReader(new StringReader(day));
        JsonObject rootObject = reader.readObject();
        reader.close();

        Queue<JsonStructure> queue = new LinkedList<>();
        queue.add(rootObject);

        int sum = 0;
        while (!queue.isEmpty()) {
            if (queue.peek() instanceof JsonObject) {
                JsonObject currentObject = (JsonObject) queue.poll();
                if (hasRedAttribute(currentObject)) {
                    continue;
                }

                for (Map.Entry<String, JsonValue> entry : currentObject.entrySet()) {
                    if (entry.getValue().getValueType() == JsonValue.ValueType.NUMBER) {
                        sum += Integer.parseInt(entry.getValue().toString());
                    } else if (entry.getValue().getValueType() == JsonValue.ValueType.OBJECT) {
                        queue.add(entry.getValue().asJsonObject());
                    } else if (entry.getValue().getValueType() == JsonValue.ValueType.ARRAY) {
                        queue.add(entry.getValue().asJsonArray());
                    }
                }
            }

            else {
                JsonArray currentArray = (JsonArray) queue.poll();
                for(JsonValue value : currentArray) {
                    if (value.getValueType() == JsonValue.ValueType.NUMBER) {
                        sum += Integer.parseInt(value.toString());
                    } else if (value.getValueType() == JsonValue.ValueType.OBJECT) {
                        queue.add(value.asJsonObject());
                    } else if (value.getValueType() == JsonValue.ValueType.ARRAY) {
                        queue.add(value.asJsonArray());
                    }
                }
            }
        }
        return String.valueOf(sum);
    }

    private static boolean hasRedAttribute(JsonObject object) {
        for (Map.Entry<String, JsonValue> entry : object.entrySet()) {
            if (entry.getValue().getValueType() == JsonValue.ValueType.STRING && "\"red\"".equals(entry.getValue().toString())) {
                return true;
            }
        }

        return false;
    }
}
