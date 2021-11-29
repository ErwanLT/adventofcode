package aoc.day19;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Data
@RequiredArgsConstructor
public class Rule {

    public final int id;
    public final List<List<Rule>> children = new ArrayList<>();
    public boolean isLiteral;
    public String raw;

    String genRegex(boolean recursive) {
        if (isLiteral) {
            return raw;
        } else if (recursive && id == 8) {
            return '(' + children.get(0).get(0).genRegex(false) + "+" + ')';
        } else if (recursive && id == 11) {
            String first = children.get(0).get(0).genRegex(false);
            String second = children.get(0).get(1).genRegex(false);
            return gen(15, (i, builder) -> builder
                    .append(first).append('{').append(i + 1).append('}')
                    .append(second).append('{').append(i + 1).append('}'));
        }

        return gen(children.size(), (i, builder) -> {
            for (Rule rule : children.get(i)) {
                builder.append(rule.genRegex(recursive));
            }
        });
    }

    private String gen(int length, BiConsumer<Integer, StringBuilder> consumer) {
        StringBuilder builder = new StringBuilder("(");

        for (int i = 0; i < length; i++) {
            consumer.accept(i, builder);
            if (i < length - 1)
                builder.append('|');
        }

        return builder.append(')').toString();
    }
}

