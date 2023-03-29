package nai01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class KNN {
    private int k;
    private double dataSetSize = 0;
    private double identifiedInstancesNumber = 0;
    private int attributesCount = 0;
    private final ArrayList<String> trainingFileContents = new ArrayList<>();
    private final ArrayList<String> testFileContents = new ArrayList<>();

    public void readFile(String path, String testORtraining) {
        // This method puts the data from the file into the test list
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                line = line.replace(",", ".");
                if(testORtraining.equals("test")){
                    testFileContents.add(line);
                    dataSetSize++;
                }else{
                    trainingFileContents.add(line);
                }
                line = br.readLine();
            }
            br.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.err.println("readFile, file was not found");
        }catch (IOException e1){
            e1.printStackTrace();
            System.err.println("BufferedReader failed to read lines");
        }
    }

    public void comparing() {
        //it compares the nodes to determine which are the closest
        Scanner sc = new Scanner(System.in);
        k = sc.nextInt();
        double accuracy;
        int matchedNeighborsCounter=0;
        // it takes each attribute from the string array and moves it to a double array
        // that it can be counted further. only the last attribute it inserts as a name it can be classified later.
        for(String s : testFileContents) {
            ArrayList<String> neighbors = new ArrayList<>();
            ArrayList<String> closestNeighbors = new ArrayList<>();
            String testingName = "";
            matchedNeighborsCounter = 0;
            String[] line = s.split("\\s+");
            attributesCount = line.length-1;
            double[] testingValues = new double[line.length];
            //takes a list of nearest neighbours and checks for each whether the name matches the name of the test object
            // and if so, then we add one to the variable, if the nearest neighbours with this name are the majority,
            //then this means that we have correctly classified the object
            for(int i= 1;i<testingValues.length;i++) {
                if (i == (testingValues.length-1)) {
                    testingName = line[i];
                } else {
                    testingValues[i] = Double.parseDouble(line[i]);
                }
            }
            calculations(k, neighbors, closestNeighbors, testingValues);

            for(int i = 0; i<k; i++) {
                if(closestNeighbors.get(i).contains(testingName)) {
                    matchedNeighborsCounter++;
                }
            }
            if(matchedNeighborsCounter > (k/2)){
                identifiedInstancesNumber++;
            }
        }
        accuracy = ((identifiedInstancesNumber * 100)/ dataSetSize);
        System.out.println("Classified correctly: " + identifiedInstancesNumber + " examples");
        System.out.println("Accuracy is: " + accuracy + "%");
    }
    //This method checks which are the closes neighbours and puts them in a list
    private void calculations(int k, List<String> neighbors, List<String> closestNeighbors, double[] testingValues) {
        // method with vectors distance is a vector through the vector it looks for neighbours and then adds them to the neighbours and sorts
        for(String s : trainingFileContents) {
            String[] trainingLine = s.split("\\s+");
            double[] trainingValues = new double[trainingLine.length];
            double distance = 0;
            String trainingName = "";
            for(int i= 1;i<trainingLine.length;i++) {
                if (i == (trainingLine.length - 1)) {
                    trainingName = trainingLine[i];
                } else {
                    trainingValues[i] = Double.parseDouble(trainingLine[i]);
                }
            }
            for(int i=1;i<trainingValues.length;i++){
                distance +=Math.pow((testingValues[i]-trainingValues[i]),2);
            }
            distance = Math.sqrt(distance);
            neighbors.add(distance + " " + trainingName);
            Collections.sort(neighbors);
        }
        for(int i = 0; i <k; i++) {
            closestNeighbors.add(neighbors.get(i));
        }
    }

    public void newInstance() {
        // this one allowed you to check your own vector
        Scanner sc = new Scanner(System.in);
        double[] inputValues = new double[attributesCount+1];
        for(int i=1;i<attributesCount;i++){
            System.out.println("Please specify "+i+" attribute");
            inputValues[i] = sc.nextDouble();
        }
        int irisSetosa=0;
        int irisVersicolor=0;
        int irisVirginica=0;
        ArrayList<String> neighbors = new ArrayList<>();
        ArrayList<String> ClosestNeighbors = new ArrayList<>();
        calculations(k, neighbors, ClosestNeighbors, inputValues);
        for(int i = 0; i<k; i++) {
            if(ClosestNeighbors.get(i).contains("Iris-setosa")) {
                irisSetosa++; }
            else if(ClosestNeighbors.get(i).contains("Iris-versicolor")) {
                irisVersicolor++; }
            else if(ClosestNeighbors.get(i).contains("Iris-virginica")) {
                irisVirginica++; }
        }

        if(irisSetosa > irisVersicolor && irisSetosa > irisVirginica) {
            System.out.println("it's iris-setosa");
        }else if(irisVersicolor > irisSetosa && irisVersicolor > irisVirginica) {
            System.out.println("it's iris-versicolor");
        }else {
            System.out.println("it's iris-virginica");
        }
    }
}

