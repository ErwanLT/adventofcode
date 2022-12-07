package aoc.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
