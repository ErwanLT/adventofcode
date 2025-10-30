package aoc.day02;

public final class Box {

    public final int l;
    public final int w;
    public final int h;

    public Box(int l, int w, int h) {
        this.l = l;
        this.w = w;
        this.h = h;
    }

    public int getRequiredWrappingPaper() {
        int wrappingPaper = 2 * (l * w) + 2 * (w * h) + 2 * (h * l);
        int slack = Math.min(Math.min(l * w, w * h), h * l);
        return wrappingPaper + slack;
    }

    public int getRibbonLength() {
        int wrappingPresent = 0;
        if (l >= w && l >= h) {
            wrappingPresent += 2 * w + 2 * h;
        } else if (w >= h && w >= l) {
            wrappingPresent += 2 * l + 2 * h;
        } else if (h >= l && h >= w) {
            wrappingPresent += 2 * w + 2 * l;
        }
        int bow = l * w * h;
        return wrappingPresent + bow;
    }

    public int getL() {
        return this.l;
    }

    public int getW() {
        return this.w;
    }

    public int getH() {
        return this.h;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Box)) return false;
        final Box other = (Box) o;
        if (this.getL() != other.getL()) return false;
        if (this.getW() != other.getW()) return false;
        if (this.getH() != other.getH()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getL();
        result = result * PRIME + this.getW();
        result = result * PRIME + this.getH();
        return result;
    }

    public String toString() {
        return "Box(l=" + this.getL() + ", w=" + this.getW() + ", h=" + this.getH() + ")";
    }
}
