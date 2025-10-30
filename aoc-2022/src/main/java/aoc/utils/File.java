package aoc.utils;

public class File {
    private long size;
    private String name;

    public File(long size, String name) {
        this.size = size;
        this.name = name;
    }

    public File() {
    }

    public long getSize() {
        return this.size;
    }

    public String getName() {
        return this.name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }
}
