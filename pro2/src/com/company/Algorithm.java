package com.company;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
    // I have a vector as an arraylist

public class Algorithm {
    final List<Iris> training; // the training set
    int iterations; // //the amount of times I want to go through the training set
    final static List<Double> list_of_weights = new ArrayList<>(); //the weight vector
    static double LEARNING_RATE = 0.01;   //the learning rate aka alpha
    static double THRESHOLD = Math.random();   //theta

    //constructor that starts weight vector with random coordinates
    public Algorithm(List<Iris> training, int iterations) {
        this.training = training;
        this.iterations = iterations;
        peekRandomNumberForWeightsCoor();
    }

    private void peekRandomNumberForWeightsCoor() {
        for (int i = 0; i < training.get(0).getCoordinates().size(); i++)
            list_of_weights.add(new Random().nextDouble());
        learn();
    }

    public void learn(){
        for (int k = 0; k < iterations; k++) {
            for (int i = 0; i < training.size(); i++) {
                for (int j = 0; j < training.get(i).getCoordinates().size(); j++) {
                    double newWeight = training.get(i).getCoordinates().get(j) + LEARNING_RATE
                            * (training.get(i).getNumber_of_type() - getResult(training.get(i)))
                            * training.get(i).getCoordinates().get(j);
                    list_of_weights.set(j, newWeight);
                }
                THRESHOLD = THRESHOLD -  LEARNING_RATE * (training.get(i).getNumber_of_type() - getResult(training.get(i)));
                THRESHOLD = Double.parseDouble(new DecimalFormat("##.##").format(THRESHOLD));
            }
        }
    }

    private int getResult(Iris iris) {
        int result = 0;
        for (int i = 0; i < iris.getCoordinates().size(); i++) {
            result += iris.getCoordinates().get(i)*list_of_weights.get(i);
        }
        return result >= THRESHOLD ? 1 : 0;
    }

    static void test(List<Iris> test){
        double error = 0;
        double total = 0;
        for (int i = 0; i < test.size(); i++) {
            int result = 0;
            for (int j = 0; j < test.get(i).getCoordinates().size(); j++) {                                                     //loop goes through each coordinate in vector
                result += test.get(i).getCoordinates().get(j) * list_of_weights.get(j);                                     //calculates scalar going through each coordinate
            }
            if(result >=THRESHOLD){
                System.out.println("Found: virginica, real: " + test.get(i).getName());
                if(!"virginica".equals(test.get(i).getName())) error++;
            }
            else {
                System.out.println("Found: versicolor, real: " + test.get(i).getName());
                if(!"versicolor".equals(test.get(i).getName())) error++;
            }

            total++;
        }
        System.out.println("Error: " + Double.parseDouble(new DecimalFormat("##.##").format(error * 100 / total)) + "%");
    }

}
