package com.company;

import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static int iterations = 1000;

    public static void main(String[] args) {
        start();
    }

    public static void start(){
        System.out.println("Press 1 to start training");
        System.out.println("Press 2 to start test");
        System.out.println("Press 3 to exit");
        String trainingExample = "perceptron.data.txt";
        String testExample = "perceptron.test.data.txt";
        int fromUser = sc.nextInt();
        switch (fromUser) {

            case 1 -> new DataReader(trainingExample, iterations);
            case 2 -> new DataReader(testExample);
            case 3 -> System.exit(1);
        }
        start();
    }
}
