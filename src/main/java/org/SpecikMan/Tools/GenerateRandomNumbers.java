package org.SpecikMan.Tools;

public class GenerateRandomNumbers {
    public static String generate(){
        long min = 100000;
        long max = 999999;

        //Generate random int value from 50 to 100
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        return String.valueOf(random_int);
    }
}
