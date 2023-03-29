package com.company;

public class LetterCounter
{
    private static String letters = "abcdefghijklmnopqrstuvwxyz";

    public static Vector getLettersVector(String str) throws Exception
    {
        str = str.toLowerCase();
        Vector temp = new Vector(letters.length());

        for (int i = 0; i < str.length(); i++)
        {
            for (int j = 0; j < letters.length(); j++)
            {
                if (Character.toLowerCase(str.charAt(i)) == Character.toLowerCase(letters.charAt(j)))
                {
                    temp.setValue(j, temp.getValue(j) + 1);
                }
            }
        }
        return temp.normalize();
    }
}
