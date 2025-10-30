package aoc.utils;

public class FileNode {
    private String name;
    private int size;

    public FileNode(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public FileNode() {
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
