package aoc.utils;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String name;
    private Node parent;
    private List<Node> children = new ArrayList<>();
    private List<FileNode> files = new ArrayList<>();
    private int size = 0;

    public Node(String name, Node parent) {
        this.name = name;
        this.parent = parent;
    }

    public Node(String name, Node parent, List<Node> children, List<FileNode> files, int size) {
        this.name = name;
        this.parent = parent;
        this.children = children;
        this.files = files;
        this.size = size;
    }

    public Node() {
    }

    public String getName() {
        return this.name;
    }

    public Node getParent() {
        return this.parent;
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public List<FileNode> getFiles() {
        return this.files;
    }

    public int getSize() {
        return this.size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void setFiles(List<FileNode> files) {
        this.files = files;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
