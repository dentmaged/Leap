package net.avicus.leap.util;

import java.util.Random;

public class RandomUtils {

    private static Random random = new Random();

    public static Double nextDouble() {
        return random.nextDouble();
    }

    public static int between(int first, int second) {
        int min = Math.min(first, second);
        int max = Math.max(first, second);
        
        return random.nextInt(max - min + 1) + min;
    }

}
