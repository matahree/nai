package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main
{
    private static PerceptronBrain brain;


    public static List<Directory> dirList = Arrays.asList(
            new Directory("lang/English"),
            new Directory("lang/German"),
            new Directory("lang/Polish"));

    public static List<Directory> testDirList = Arrays.asList(
            new Directory("lang.test/English"),
            new Directory("lang.test/German"),
            new Directory("lang.test/Polish"));

    private static int repeatLearning = 100;
    private static double wantedAccuracy = 1f;

    public static void main(String[] args)
    {
        int counter =0;
        double accuracy = 0;
        try
        {
            brain = new PerceptronBrain(dirList,0.01);

            while(counter < repeatLearning && accuracy < wantedAccuracy)
            {
                brain.learn();
                accuracy = brain.classifyAll(dirList,false);
                counter++;
            }
            System.out.println("\n Learning repeated: " + counter + "times | Accuracy: " + accuracy+ " \n");

            double testAccuracy = brain.classifyAll(testDirList,true);

            System.out.println("\n  Test set Accuracy: " + testAccuracy+ " \n");

            Scanner scanner = new Scanner(System.in).useDelimiter("\n");
            String command;
            do
            {
                System.out.println("\n  Enter text to classify  ");
                command = scanner.next();
                String result = brain.classify(LetterCounter.getLettersVector(command));
                System.out.print(command + " = " + result + "\n");
            }
            while(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
