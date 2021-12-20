package aoc.day20;

import aoc.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Day20 implements Day {

    private static String algo;
    private static Image image;

    @Override
    public String part1(List<String> input) {
        algo = input.get(0);
        image = parseImage(input.stream().skip(2).collect(Collectors.toList()));

        image = enhanceImage(image, algo);
        image = enhanceImage(image, algo);

        return String.valueOf(image.pixels().size());
    }

    @Override
    public String part2(List<String> input) {
        for (int i = 0; i < 48; i++) {
            image = enhanceImage(image, algo);
        }

        return String.valueOf(image.pixels().size());
    }

    static Image enhanceImage(Image input, String algorithm) {
        Set<Pixel> output = input.pixels();
        int increase = 1;
        boolean background = false;
        for (int extend = 1; increase > 0 && !background; extend++) { // enhance in loop each time extending border 1 pixel further
            int prevCount = output.size();
            output = enhancePixelslWithExtendedSize(extend, input, algorithm);
            increase = output.size()-prevCount;
            int borderSize = 2*(input.x2()-input.x1() + extend*2) + 2*(input.y2()-input.y1() + extend*2);
            background = borderSize == increase;  // all the pixels in border are set -> background changed color to 1
        }
        return Image.create(background, output);
    }

    static Set<Pixel> enhancePixelslWithExtendedSize(int extend, Image image, String algorithm) {
        Set<Pixel> output = new HashSet<>();
        for (int y = image.y1() - extend; y <= image.y2() + extend; y++) {
            for (int x = image.x1() - extend; x <= image.x2() + extend; x++) {
                enhancePixel(new Pixel(x, y), image, algorithm).ifPresent(output::add);
            }
        }
        return output;
    }

    static Optional<Pixel> enhancePixel(Pixel p, Image source, String algorithm) {
        int bits = 0;
        for (int y = p.y()-1; y <= p.y()+1; y++) {
            for (int x = p.x()-1; x <= p.x()+1; x++) {
                bits = (bits << 1) + (source.bit(x, y) ? 1 : 0);
            }
        }
        return algorithm.charAt(bits) == '.' ? Optional.empty() : Optional.of(p);
    }

    static Image parseImage(List<String> data) {
        Set<Pixel> pixels = new HashSet<>();
        for (int y = 0; y < data.size(); y++) {
            String line = data.get(y);
            for (int x=0; x < line.length(); x++) {
                if (line.charAt(x) != '.') {
                    pixels.add(new Pixel(x,y));
                }
            }
        }
        return Image.create(false, pixels);
    }
}
