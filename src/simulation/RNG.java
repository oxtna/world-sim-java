package simulation;

import java.util.Random;

public final class RNG {
    private static final Random random = new Random();

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
