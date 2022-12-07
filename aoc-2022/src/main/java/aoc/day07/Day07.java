package aoc.day07;

import aoc.Day;
import aoc.utils.Computer;
import aoc.utils.Node;

import java.util.ArrayList;
import java.util.List;

public class Day07 implements Day {

    @Override
    public String part1(List<String> input) {
        Node root = new Node("/", null);
        Node pointer = root;

        Computer computer = new Computer(input, pointer, root);
        computer.computeNodes();

        computer.setSizes();

        List<Node> smallNodes = getSmallNodes(computer.getRoot(), 100_000);

        var sum = smallNodes.stream().mapToInt(Node::getSize).sum();

        return String.valueOf(sum);
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

        Computer computer = new Computer(input, pointer, root);
        computer.computeNodes();

        computer.setSizes();

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
