package aoc.day11;

public class Monkey {
    private long n;
    private String items;
    private char op;
    private String add;
    private long divisible;
    private long ifTrue;
    private long ifFalse;

    public Monkey(long n, String items, char op, String add, long divisible, long ifTrue, long ifFalse) {
        this.n = n;
        this.items = items;
        this.op = op;
        this.add = add;
        this.divisible = divisible;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    public Monkey() {
    }

    public long getN() {
        return this.n;
    }

    public String getItems() {
        return this.items;
    }

    public char getOp() {
        return this.op;
    }

    public String getAdd() {
        return this.add;
    }

    public long getDivisible() {
        return this.divisible;
    }

    public long getIfTrue() {
        return this.ifTrue;
    }

    public long getIfFalse() {
        return this.ifFalse;
    }

    public void setN(long n) {
        this.n = n;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setOp(char op) {
        this.op = op;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public void setDivisible(long divisible) {
        this.divisible = divisible;
    }

    public void setIfTrue(long ifTrue) {
        this.ifTrue = ifTrue;
    }

    public void setIfFalse(long ifFalse) {
        this.ifFalse = ifFalse;
    }
}