package aoc.day13;

public record Machine(long aX, long aY, long bX, long bY, long prizeX, long prizeY) {
        public Machine withPrize(long prizeX, long prizeY) {
            return new Machine(aX, aY, bX, bY, prizeX, prizeY);
        }

        public long fewestTokens() {
            long numerator = prizeX * aY - prizeY * aX;
            long b = numerator / (bX * aY - bY * aX);
            long remX = prizeX - b * bX;
            long l = aX == 0 ? prizeY : remX;
            long r = aX == 0 ? aY : aX;
            long a = l / r;
            return (a * aY + b * bY == prizeY && l % r == 0) ? 3 * a + b : 0;
        }
    }