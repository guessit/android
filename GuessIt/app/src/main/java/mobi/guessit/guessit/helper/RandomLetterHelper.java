package mobi.guessit.guessit.helper;

import java.util.Random;

public class RandomLetterHelper {

    private static final String VOWELS = "AEIOU";
    private static final String CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZ";

    private static RandomLetterHelper instance;

    private RandomLetterHelper() {
    }

    public static RandomLetterHelper getInstance() {
        if (instance == null) instance = new RandomLetterHelper();
        return instance;
    }

    public String randomVowel() {
        int index = new Random().nextInt(VOWELS.length());
        return String.valueOf(VOWELS.charAt(index));
    }

    public String randomConsonant() {
        int index = new Random().nextInt(CONSONANTS.length());
        return String.valueOf(CONSONANTS.charAt(index));
    }
}
