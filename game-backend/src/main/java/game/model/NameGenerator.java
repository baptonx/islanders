package game.model;

import java.util.Random;

public class NameGenerator {

    static Random rnd = new Random();// random is used for randomly select consonance and vowels from given list

    static final String CONS = "zxcvbnmlkjhgfdsqwrtyp";  //String which store the consonances

    static final String VOWELS = "aeiou";  //String which store vowels

    public String generateName(final int len)  //len define length of names
    {
        final StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            if (i % 2 == 0) {
                sb.append(CONS.charAt(rnd.nextInt(CONS.length())));
            } else {
                sb.append(VOWELS.charAt(rnd.nextInt(VOWELS.length())));
            }
        }
        return sb.toString();
    }

}
