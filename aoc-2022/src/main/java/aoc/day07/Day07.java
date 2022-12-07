package aoc.day07;

import aoc.Day;

import java.util.ArrayList;
import java.util.List;

public class Day07 implements Day {

    @Override
    public String part1(List<String> input) {
        Node root = new Node("/", null);
        Node pointer = root;

        parcoursNodes(input, pointer, root);

        setSizes(root);

        List<Node> smallNodes = getSmallNodes(root, 100_000);

        var sum = smallNodes.stream().mapToInt(Node::getSize).sum();

        return String.valueOf(sum);
    }

    private void parcoursNodes(List<String> input, Node pointer, Node root) {
        for (String line : input) {
            String[] parts = line.split(" ");

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

    public int setSizes(Node node) {
        int total = node.getChildren().stream().mapToInt((dirNode) -> setSizes(dirNode)).sum() + node.getFiles().stream().mapToInt((file) -> file.getSize()).sum();
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

    @Override
    public String part2(List<String> input) {
        Node root = new Node("/", null);
        Node pointer = root;

        parcoursNodes(input, pointer, root);

        setSizes(root);

        int threshold = root.getSize() - 40_000_000;
        Node min = getMinimumToDelete(root, root, threshold);

        return String.valueOf(min.getSize());
    }

    public Node getMinimumToDelete(Node node, Node minSoFar, int threshold) {
        for (Node child : node.getChildren()) {
            Node minNode = getMinimumToDelete(child, minSoFar, threshold);

            if (minNode.getSize() < minSoFar.getSize()) {
                minSoFar = minNode;
            }
        }

        if (threshold <= node.getSize() && node.getSize() < minSoFar.getSize()) {
            minSoFar = node;
        }

        return minSoFar;
    }
}
