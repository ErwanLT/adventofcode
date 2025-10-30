package aoc.day19;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Rule {

    public final int id;
    public final List<List<Rule>> children = new ArrayList<>();
    public boolean isLiteral;
    public String raw;

    public Rule(int id) {
        this.id = id;
    }

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

    public int getId() {
        return this.id;
    }

    public List<List<Rule>> getChildren() {
        return this.children;
    }

    public boolean isLiteral() {
        return this.isLiteral;
    }

    public String getRaw() {
        return this.raw;
    }

    public void setLiteral(boolean isLiteral) {
        this.isLiteral = isLiteral;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Rule)) return false;
        final Rule other = (Rule) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$children = this.getChildren();
        final Object other$children = other.getChildren();
        if (this$children == null ? other$children != null : !this$children.equals(other$children)) return false;
        if (this.isLiteral() != other.isLiteral()) return false;
        final Object this$raw = this.getRaw();
        final Object other$raw = other.getRaw();
        if (this$raw == null ? other$raw != null : !this$raw.equals(other$raw)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Rule;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        final Object $children = this.getChildren();
        result = result * PRIME + ($children == null ? 43 : $children.hashCode());
        result = result * PRIME + (this.isLiteral() ? 79 : 97);
        final Object $raw = this.getRaw();
        result = result * PRIME + ($raw == null ? 43 : $raw.hashCode());
        return result;
    }

    public String toString() {
        return "Rule(id=" + this.getId() + ", children=" + this.getChildren() + ", isLiteral=" + this.isLiteral() + ", raw=" + this.getRaw() + ")";
    }
}

