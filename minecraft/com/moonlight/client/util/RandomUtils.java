package com.moonlight.client.util;

import java.util.Random;

public class RandomUtils {

	public static String generateRandomName(long seed) {
        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random(seed);

        StringBuilder randomName = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            randomName.append(characters.charAt(random.nextInt(characters.length())));
        }

        return randomName.toString();
    }
	
	private static long hashString(String s) {
        long hash = 7;
        for (int i = 0; i < s.length(); i++) {
            hash = hash * new Random(System.currentTimeMillis()).nextInt(80) + s.charAt(i);
        }
        return hash;
    }
	
}
