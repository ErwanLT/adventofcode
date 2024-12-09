package aoc.day09;

import aoc.Day2024;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Day09 extends Day2024 {

    public Day09() {
        super(9, "Disk Fragmenter");
    }

    public static void main(String[] args) {
        new Day09().printParts();
    }

    @Override
    public Object part1() {
        String s = day();
        List<Object> blocks = createBlocksForPart1(s);
        compactBlocks(blocks);
        return calculateChecksum(blocks);
    }

    @Override
    public Object part2() {
        String s = day();
        List<Block> blocks = createBlocksForPart2(s);
        compactBlocksWithSizes(blocks);
        return calculateChecksum(expandBlocks(blocks));
    }

    // Méthode pour créer les blocs pour la partie 1
    private List<Object> createBlocksForPart1(String input) {
        List<Object> blocks = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            int length = Character.getNumericValue(input.charAt(i));
            for (int j = 0; j < length; j++) {
                blocks.add(i % 2 == 0 ? i / 2 : ".");
            }
        }
        return blocks;
    }

    // Méthode pour créer les blocs pour la partie 2
    private List<Block> createBlocksForPart2(String input) {
        List<Block> blocks = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            int value = Character.getNumericValue(input.charAt(i));
            blocks.add(i % 2 == 0 ? new Block(i / 2, value) : new Block(".", value));
        }
        return blocks;
    }

    // Méthode pour compacter les blocs (partie 1)
    private void compactBlocks(List<Object> blocks) {
        int i = 0;
        int j = blocks.size() - 1;
        while (i < j) {
            if (!blocks.get(i).equals(".")) i++;
            if (blocks.get(j).equals(".")) j--;
            if (blocks.get(i).equals(".") && !blocks.get(j).equals(".")) {
                Object temp = blocks.get(i);
                blocks.set(i, blocks.get(j));
                blocks.set(j, temp);
            }
        }
    }

    // Méthode pour compacter les blocs avec tailles (partie 2)
    private void compactBlocksWithSizes(List<Block> blocks) {
        for (int currentId = (blocks.size() - 1) / 2; currentId >= 0; currentId--) {
            int lastIndex = -1;

            // Trouver le dernier bloc correspondant à `currentId`
            for (int j = blocks.size() - 1; j >= 0; j--) {
                if (blocks.get(j).getId().equals(currentId)) {
                    lastIndex = j;
                    break;
                }
            }

            if (lastIndex == -1) continue;

            // Trouver un espace libre suffisamment grand
            for (int k = 0; k < lastIndex; k++) {
                if (blocks.get(k).getId().equals(".") && blocks.get(k).getSize() >= blocks.get(lastIndex).getSize()) {
                    Block temp = blocks.get(k);
                    Block file = blocks.get(lastIndex);

                    // Déplacement
                    blocks.set(k, file);
                    blocks.set(lastIndex, new Block(".", file.getSize()));

                    // Créer un espace libre restant si nécessaire
                    int remainingSize = temp.getSize() - file.getSize();
                    if (remainingSize > 0) {
                        blocks.add(k + 1, new Block(".", remainingSize));
                    }
                    break;
                }
            }
        }
    }

    // Méthode pour étendre les blocs en une liste plate (partie 2)
    private List<Object> expandBlocks(List<Block> blocks) {
        List<Object> expanded = new ArrayList<>();
        for (Block block : blocks) {
            for (int i = 0; i < block.getSize(); i++) {
                expanded.add(block.getId());
            }
        }
        return expanded;
    }

    // Méthode commune pour calculer le checksum
    private long calculateChecksum(List<Object> blocks) {
        long checksum = 0;
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) instanceof Integer id) {
                checksum += (long) i * id;
            }
        }
        return checksum;
    }

    // Classe représentant un bloc (fichier ou espace libre)
    static class Block {
        @Getter
        private final Object id; // Soit un entier (ID fichier), soit une chaîne "."
        @Getter
        private final int size; // Taille du bloc

        public Block(Object id, int size) {
            this.id = id;
            this.size = size;
        }

        @Override
        public String toString() {
            return "(" + id + ", " + size + ")";
        }
    }
}