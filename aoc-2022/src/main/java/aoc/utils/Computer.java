package aoc.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Computer {

    private List<String> commands;
    private Node pointer;
    private Node root;

    public void computeNodes(){
        for (String command : commands) {
            String[] parts = command.split(" ");

            if (parts[1].equals("ls")) {

            } else if (parts[1].equals("cd")) {
                if (parts[2].equals("/")) {
                    pointer = root;
                } else if (parts[2].equals("..")) {
                    pointer = pointer.getParent();
                } else {
                    pointer = pointer.getChildren().stream().filter((node -> node.getName().equals(parts[2]))).toList().get(0);
                }
            } else if (parts[0].equals("dir")) {
                pointer.getChildren().add(new Node(parts[1], pointer));
            } else {
                pointer.getFiles().add(new FileNode(parts[1], Integer.parseInt(parts[0])));
            }
        }
    }

    public int setSizes() {
        int total = root.getChildren().stream().mapToInt(this::setSizeNode).sum() + root.getFiles().stream().mapToInt(FileNode::getSize).sum();
        root.setSize(total);
        return total;
    }

    public int setSizeNode(Node node){
        int total = node.getChildren().stream().mapToInt(this::setSizeNode).sum() + node.getFiles().stream().mapToInt(FileNode::getSize).sum();
        node.setSize(total);
        return total;
    }

    public List<Node> getSmallNodes(Node node, int threshold) {
        List<Node> nodes = new ArrayList<>();
        for (Node child : node.getChildren()) {
            nodes.addAll(getSmallNodes(child, threshold));
        }

        if (node.getSize() < threshold) {
            nodes.add(node);
        }

        return nodes;
    }


}
