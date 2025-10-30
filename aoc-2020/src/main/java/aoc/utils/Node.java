package aoc.utils;

public class Node {

    public int value;
    public Node next;
    public Node prev;

    public Node(int value) {
        this.value = value;
    }

    public Node() {
    }

    @Override
    public String toString() {
        return "" + value + "";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Node)) return false;
        final Node other = (Node) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getValue() != other.getValue()) return false;
        final Object this$next = this.getNext();
        final Object other$next = other.getNext();
        if (this$next == null ? other$next != null : !this$next.equals(other$next)) return false;
        final Object this$prev = this.getPrev();
        final Object other$prev = other.getPrev();
        if (this$prev == null ? other$prev != null : !this$prev.equals(other$prev)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Node;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getValue();
        final Object $next = this.getNext();
        result = result * PRIME + ($next == null ? 43 : $next.hashCode());
        final Object $prev = this.getPrev();
        result = result * PRIME + ($prev == null ? 43 : $prev.hashCode());
        return result;
    }

    public int getValue() {
        return this.value;
    }

    public Node getNext() {
        return this.next;
    }

    public Node getPrev() {
        return this.prev;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}
