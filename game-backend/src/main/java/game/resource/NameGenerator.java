package game.resource;

import java.util.Random;

public class NameGenerator {

    static Random rnd = new Random();// random is used for randomly select consonance and vowels from given list

    static final String CONS = "zxcvbnmlkjhgfdsqwrtyp"; //String which store the consonances

    static final String VOWELS = "aeiou";//String which store vowels

    public String generateName(int len) //len define length of names
    {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            if (i % 2 == 0)
                sb.append(CONS.charAt(rnd.nextInt(CONS.length())));
            else sb.append(VOWELS.charAt(rnd.nextInt(VOWELS.length())));
        }
        System.out.println(sb);
        return sb.toString();
    }
    /*
    public static void main(String args[])// mains function
    {
        nameGenerator name = new nameGenerator();// create an object of above class
        System.out.println("name with length 4: " + name.generateName(4));
        System.out.println("name with length 6: " + name.generateName(6));
    }
     */
}