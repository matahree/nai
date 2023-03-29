package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    List<Iris> trainingFile; //training set
    static List<Iris> testFile = new ArrayList<>();  //the file with the test set
    int iterations;  //amount of iterations for training

    //constructors that take a file and test from it
    public DataReader(String trainingExample, int iterations) {
        trainingFile = readFromFile(trainingExample);
        this.iterations = iterations;
        new Algorithm(trainingFile, iterations);
    }
    public DataReader(String testExample){
        testFile = readFromFile(testExample);
        Algorithm.test(testFile);
    }

    public DataReader(String trainingExample, String testExample, String line) {
        trainingFile = readFromFile(trainingExample);
        testFile = readFromFile(testExample);
        List<Iris> tmp = new ArrayList<>();
        tmp.add(readFromLine(line));
        Algorithm.test(tmp);
    }
    //creates a vector form a line in the file
    public Iris readFromLine(String line) {
        String[] ln = line.replaceAll("\"", "").split(",");
        List<Double> ys = new ArrayList<>();
        for (int i = 0; i < ln.length - 1; i++){
            ys.add(Double.parseDouble(ln[i]));
        }

        return new Iris(ln[ln.length - 1].replaceFirst("Iris-", ""), ys);
    }
    //creates a list of Vectors from a file using the above method
    public List<Iris> readFromFile(String trainingExample) {
        List<Iris> tmp = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream((trainingExample))));
            String line;
            while ((line = br.readLine()) != null){
                tmp.add(readFromLine(line));
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }
}
